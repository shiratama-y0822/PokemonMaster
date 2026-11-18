"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import type { ReactNode } from "react";

const menuItems = [
  { label: "ポケモン図鑑", href: "/pokedex" },
  { label: "ポケモンボックス", href: "/boxes" },
  { label: "パーティ一覧", href: "/parties" },
];

export default function MainLayout({ children, }: { children: ReactNode }) {
  const pathname = usePathname();
  const showSidebar = ![
    "/",
    "/home",
    "/register",
    "/login-failed",
    "/type-aisyou",
  ].includes(pathname);

  return (
    <div className="min-h-screen bg-slate-50 text-slate-900">
      <header className="flex h-16 items-center justify-between border-b bg-white px-6">
        {showSidebar ? (
          <Link href="/common" className="text-xl font-bold">
            Pokemon Master
          </Link>
        ) : (
          <span className="text-xl font-bold">Pokemon Master</span>
        )}
        <Link
          href="/type-aisyou"
          className="rounded border px-4 py-2 hover:bg-slate-100"
        >
          タイプ相性表
        </Link>
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
          </aside>
        )}

        <main className="min-w-0 flex-1 p-8">{children}</main>
      </div>
    </div>
  );
}