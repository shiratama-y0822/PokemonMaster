"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

const menuItems = [
  { label: "ポケモン図鑑", href: "/pokedex" },
  { label: "ポケモンボックス", href: "/boxes" },
  { label: "パーティ一覧", href: "/parties" },
  { label: "相性表", href: "/type-aisyou" },
];

export default function Sidebar() {
  const pathname = usePathname();

  return (
    <nav aria-label="メインナビゲーション" className="space-y-2">
      {menuItems.map((item) => {
        const isActive = pathname === item.href;

        return (
          <Link
            key={item.href}
            href={item.href}
            aria-current={isActive ? "page" : undefined}
            className={`block rounded px-3 py-2 ${isActive
                ? "bg-blue-100 font-semibold text-blue-900"
                : "hover:bg-slate-100"
              }`}
          >
            {item.label}
          </Link>
        );
      })}
    </nav>
  );
}