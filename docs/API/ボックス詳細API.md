# ボックス詳細API

## 基本情報

| 項目 | 内容 |
|---|---|
| API名称 | ボックス詳細API |
| HTTPメソッド | GET |
| URL | /box/{boxId} |
| 認証要否 | 要 |

## リクエスト

### パスパラメータ

| 英名 | 型 | 必須 | 備考 |
|---|---|---|---|
| pokemonId | Integer | x | 図鑑番号 |

### クエリパラメータ

| 英名 | 型 | 必須 | 備考 |
|---|---|---|---|
| name | string | x | ポケモン名 |
| type | string | x | タイプ |

### リクエストボディ

| 英名 | 型 | 必須 | 備考 |
|---|---|---|---|
なし

## レスポンス

| 英名 | 型 | 備考 |
|---|---|---|---|
| pokemonId | int | 図鑑番号 |
| name | string | 日本名 |
| boxId | int | ボックス登録ID |
| nickname | string | ニックネーム |
| gender | int | 性別 |
| category | string | ポケモンの分類 |
| type | string | タイプ |
| height | string | 高さ |
| weight | string | 重さ |
| baseStats | string | 種族値 |

## ステータスコード

| コード | 条件 |
|---|---|
| 200 | 正常終了 |

## 備考

- 画像も併せて表示する

