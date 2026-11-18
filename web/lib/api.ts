export class ApiError extends Error {
  constructor(
    public readonly status: number,
    message: string,
  ) {
    super(message);
    this.name = "ApiError";
  }
}

type ApiProblem = {
  detail?: string;
};

export async function apiRequest<T>(
  path: string,
  options: RequestInit = {},
): Promise<T | null> {
  const headers = new Headers(options.headers);

  if (typeof options.body === "string" && !headers.has("Content-Type")) {
    headers.set("Content-Type", "application/json");
  }

  let response: Response;

  try {
    response = await fetch(`/api${path}`, {
      ...options,
      headers,
      credentials: "same-origin",
      cache: "no-store",
    });
  } catch {
    throw new ApiError(0, "APIに接続できませんでした。");
  }

  if (!response.ok) {
    const problem = (await response.json().catch(() => null)) as ApiProblem | null;
    throw new ApiError(
      response.status,
      problem?.detail ?? `APIエラー（${response.status}）`,
    );
  }

  if (response.status === 204) {
    return null;
  }

  return (await response.json()) as T;
}