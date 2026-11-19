"use client"

import { ApiError, apiRequest } from "@/lib/api";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

type HomeResponse = {
  nickname: string;
  pokemonCaught: number;
}

export default function HomePage() {
  const router = useRouter();
  const [home, setHome] = useState<HomeResponse | null>(null);
  const [error, setError] = useState("");

  useEffect(() => {
    let cancelled = false;

    async function loadHome() {
      try {
        const result = await apiRequest<HomeResponse>("/home");
        if (!cancelled) setHome(result);
      } catch (cause) {
        if (cancelled) return;
        if (cause instanceof ApiError && cause.status === 401) {
          router.replace("/login");
          return;
        }
        setError("ホーム情報を取得できませんでした。");
      }
    }

    void loadHome();
    return () => {
      cancelled = true;
    };
  }, [router]);

  if (error) return <p role="alert">{error}</p>;
  if (!home) return <p>読み込み中...</p>;

  return (
    <section>
      <h1 className="text-2xl font-bold">ホーム</h1>
      <p className="mt-6">ようこそ、{home.nickname}さん</p>
      <p className="mt-2">登録ポケモン数：{home.pokemonCaught}匹</p>
    </section>
  );
}
