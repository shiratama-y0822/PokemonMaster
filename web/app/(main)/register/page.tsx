"use client"

import { ApiError, apiRequest } from "@/lib/api";
import { useRouter } from "next/navigation";
import { FormEvent, useState } from "react";

export default function RegisterPage() {
  const router = useRouter();
  const [message, setMessage] = useState("");
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (submitting) return;

    const formData = new FormData(event.currentTarget);
    const userId = String(formData.get("userId") ?? "");
    const nickname = String(formData.get("nickname") ?? "");
    const password = String(formData.get("password") ?? "");

    setMessage("");

    const errors: string[] = [];

    if (!/^[A-Za-z0-9_@-]{1,16}$/.test(userId)) {
      errors.push("ユーザーIDは半角英数字・_ @ - を使用し、1～16文字で入力してください。");
    }

    if (!/^[Ａ-Ｚａ-ｚ０-９\u3041-\u3094\u30A1-\u30FA]{1,6}$/.test(nickname)) {
      errors.push("ニックネームは全角英数字・ひらがな・カタカナで1～6文字で入力してください。");
    }

    if (!/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!?_-])[A-Za-z0-9!?_-]{8,24}$/.test(password)) {
      errors.push("パスワードは半角英大文字・小文字・数字・記号（! ? _ -）を各1文字以上使用し、8～24文字で入力してください。");
    }

    if (errors.length > 0) {
      setMessage(errors.join("\n"));
      return;
    }

    setSubmitting(true);

    try {
      await apiRequest<{ userId: string; nickname: string }>("/users", {
        method: "POST",
        body: JSON.stringify({ userId, nickname, password }),
      });

      router.push("/login");

    } catch (error) {
      if (error instanceof ApiError) {
        if (error.status === 409) {
          setMessage("そのユーザーIDは既に使用されています。");
        } else if (error.status === 400) {
          setMessage("入力内容を確認してください。");
        } else if (error.status === 0) {
          setMessage("APIに接続できませんでした。時間をおいて再度お試しください。");
        } else {
          setMessage("登録に失敗しました。もう一度お試しください。");
        }
      } else {
        setMessage("登録に失敗しました。もう一度お試しください。");
      }
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <section className="mx-auto w-full max-w-sm">
      <h1 className="text-2xl font-bold">新規登録</h1>

      <form className="mt-6 space-y-5"
        onSubmit={handleSubmit}
        onReset={() => setMessage("")}
        noValidate
      >
        <div>
          <label htmlFor="userId" className="block font-medium">
            ユーザーID
          </label>
          <input
            id="userId"
            name="userId"
            type="text"
            required
            maxLength={16}
            autoComplete="username"
            className="mt-1 w-full rounded border bg-white px-3 py-2"
          />
          <p className="mt-1 text-sm text-slate-600">
            半角英数字と_ @ - を使用し、1~16文字
          </p>
        </div>

        <div>
          <label htmlFor="nickname" className="block font-medium">
            ニックネーム
          </label>
          <input
            id="nickname"
            name="nickname"
            type="text"
            required
            maxLength={6}
            autoComplete="nickname"
            className="mt-1 w-full rounded border bg-white px-3 py-2"
          />
          <p className="mt-1 text-sm text-slate-600">
            全角英数字・ひらがな・カタカナで1~6文字
          </p>
        </div>

        <div>
          <label htmlFor="password" className="block font-medium">
            パスワード
          </label>
          <input
            id="password"
            name="password"
            type="password"
            required
            minLength={8}
            maxLength={24}
            autoComplete="new-password"
            className="mt-1 w-full rounded border bg-white px-3 py-2"
          />
          <p className="mt-1 text-sm text-slate-600">
            半角英大文字・小文字・数字と記号(! ? _  - )を各1文字以上、8~24文字
          </p>
        </div>

        <div className="flex gap-3">
          <button
            type="submit"
            disabled={submitting}
            className="rounded bg-slate-900 px-4 py-2 text-white"
          >
            登録
          </button>
          <button
            type="reset"
            className="rounded border bg-white px-4 py-2"
          >
            クリア
          </button>
        </div>
        {message && (
          <p role="status" className="whitespace-pre-line text-sm">
            {message}
          </p>
        )}
      </form >
    </section>
  );
}