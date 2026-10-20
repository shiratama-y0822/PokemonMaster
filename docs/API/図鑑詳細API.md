# 図鑑詳細API

## 基本情報

| 項目 | 内容 |
|---|---|
| API名称 | 図鑑詳細API |
| HTTPメソッド | GET |
| URL | /zukan/{pokemonId} |
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
| pokemonId | int | xx |
| name | string | xx |
| gender | int | xx |
| category | string | xx |
| type | string | xx |
| height | string | xx |
| weight | string | xx |
| baseStats | string | xx |

## ステータスコード

| コード | 条件 |
|---|---|
| 200 | 正常終了 |

## 備考

- 画像も併せて表示する

