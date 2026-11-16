import Link from "next/link";
import type { ReactNode } from "react";
import Sidebar from "./Sidebar";

export default function MainLayout({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <div className="min-h-screen bg-slate-50 text-slate-900">
      <header className="flex h-16 items-center border-b bg-white px-6">
        <Link href="/common" className="text-xl font-bold">
          Pokemon Master
        </Link>
      </header>

      <div className="flex min-h-[calc(100vh-4rem)]">
        <aside className="w-60 shrink-0 border-r bg-white p-4">
          <Sidebar />
        </aside>

        <main className="min-w-0 flex-1 p-8">{children}</main>
      </div>
    </div>
  );
}