"use client";

import { ApiError, apiRequest } from "@/lib/api";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { useEffect, useState, type ReactNode } from "react";

type HomeResponse = {
  nickname: string;
  pokemonCaught: number;
};

const menuItems = [
  { label: "ポケモン図鑑", href: "/pokedex" },
  { label: "ポケモンボックス", href: "/boxes" },
  { label: "パーティ一覧", href: "/parties" },
];

const homeItems = [
  ...menuItems,
  { label: "タイプ相性表", href: "/type-aisyou" },
];

const homeColors = [
  "bg-sky-100",
  "bg-yellow-100",
  "bg-rose-100",
  "bg-emerald-100",
];

export default function MainLayout({ children }: { children: ReactNode }) {
  const pathname = usePathname();
  const router = useRouter();
  const isHome = pathname === "/home";
  const isAuthenticatedPage = ![
    "/",
    "/register",
    "/login",
    "/login-failed",
    "/type-aisyou",
  ].includes(pathname);
  const showSidebar = isAuthenticatedPage && !isHome;

  const [home, setHome] = useState<HomeResponse | null>(null);
  const [homeError, setHomeError] = useState("");
  const [loggingOut, setLoggingOut] = useState(false);
  const [logoutError, setLogoutError] = useState("");

  useEffect(() => {
    if (!isAuthenticatedPage) return;

    let cancelled = false;

    async function loadHome() {
      try {
        const result = await apiRequest<HomeResponse>("/home");
        if (!cancelled) setHome(result);
      } catch (error) {
        if (cancelled) return;
        if (error instanceof ApiError && error.status === 401) {
          router.replace("/login");
          return;
        }
        setHomeError("ホーム情報を取得できませんでした。");
      }
    }

    void loadHome();
    return () => {
      cancelled = true;
    };
  }, [isAuthenticatedPage, router]);

  async function handleLogout() {
    if (loggingOut) return;

    setLogoutError("");
    setLoggingOut(true);

    try {
      await apiRequest<null>("/logout", { method: "POST" });
      setHome(null);
      router.replace("/login");
    } catch (error) {
      if (error instanceof ApiError && error.status === 401) {
        setHome(null);
        router.replace("/login");
        return;
      }
      setLogoutError("ログアウトに失敗しました。もう一度お試しください。");
    } finally {
      setLoggingOut(false);
    }
  }

  return (
    <div className="min-h-screen bg-slate-50 text-slate-900">
      <header className="flex h-16 items-center justify-between border-b bg-white px-6">
        {isAuthenticatedPage ? (
          <Link href="/home" className="text-xl font-bold">
            Pokemon Master
          </Link>
        ) : (
          <span className="text-xl font-bold">Pokemon Master</span>
        )}

        <div className="flex items-center gap-4">
          {isAuthenticatedPage && home && (
            <span>{home.nickname}さん</span>
          )}

          {!isHome && (
            <Link
              href="/type-aisyou"
              className="rounded border px-4 py-2 hover:bg-slate-100"
            >
              タイプ相性表
            </Link>
          )}

          {isHome && (
            <button
              type="button"
              onClick={handleLogout}
              disabled={loggingOut}
              className="rounded border px-4 py-2 hover:bg-slate-100"
            >
              ログアウト
            </button>
          )}
        </div>
      </header>

      <div className="flex min-h-[calc(100vh-4rem)]">
        {showSidebar && (
          <aside className="w-60 shrink-0 border-r bg-white p-4">
            <nav aria-label="メインナビゲーション" className="space-y-2">
              {menuItems.map((item) => (
                <Link
                  key={item.href}
                  href={item.href}
                  aria-current={pathname === item.href ? "page" : undefined}
                  className="block rounded px-3 py-2 hover:bg-slate-100"
                >
                  {item.label}
                </Link>
              ))}
            </nav>

            <button
              type="button"
              onClick={handleLogout}
              disabled={loggingOut}
              className="mt-6 w-full rounded border px-3 py-2 text-left hover:bg-slate-100"
            >
              ログアウト
            </button>
          </aside>
        )}

        <main className="min-w-0 flex-1 p-8">
          {isHome && (
            homeError ? (
              <p role="alert">{homeError}</p>
            ) : home ? (
              <div className="mx-auto max-w-xl">
                <nav
                  aria-label="ホームメニュー"
                  className="grid grid-cols-2 gap-5"
                >
                  {homeItems.map((item, index) => (
                    <Link
                      key={item.href}
                      href={item.href}
                      className={`rounded border p-6 text-center font-bold hover:brightness-95 ${homeColors[index]}`}
                    >
                      {item.label}
                    </Link>
                  ))}
                </nav>

                <div className="mx-auto mt-8 w-fit rounded border bg-violet-100 px-8 py-5 text-center">
                  <p>捕まえたポケモンの数</p>
                  <p className="text-2xl font-bold">{home.pokemonCaught}匹</p>
                </div>
              </div>
            ) : (
              <p>読み込み中...</p>
            )
          )}

          {logoutError && (
            <p role="alert" className="mt-4 text-sm text-red-600">
              {logoutError}
            </p>
          )}

          {children}
        </main>
      </div>
    </div>
  );
}