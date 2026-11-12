# ボックス詳細API

## 基本情報

| 項目 | 内容 |
|---|---|
| API名称 | ボックス詳細API |
| HTTPメソッド | GET |
| URL | /api/boxes/{boxId} |
| 認証要否 | 要 |

## リクエスト

### パスパラメータ

| 英名 | 型 | 必須 | 備考 |
|---|---|---|---|
| boxId | Integer | o | ボックス登録ID |

### クエリパラメータ

| 英名 | 型 | 必須 | 備考 |
|---|---|---|---|
なし

### リクエストボディ

| 英名 | 型 | 必須 | 備考 |
|---|---|---|---|
なし

## レスポンス

| 英名 | 型 | 備考 |
|---|---|---|---|
| pokemonId | int | 図鑑番号 |
| name | string | 日本名 |
| image | String | ポケモンの画像URL　※例）/image/{pokemonId}.png |
| boxId | int | ボックス登録ID |
| nickname | string | ニックネーム |
| gender | int | 性別 |
| category | string | ポケモンの分類 |
| type | string[] | type1・type2を表示 |
| height | number | 高さ/単位は「m」frontendで表示 |
| weight | number | 重さ/単位は「kg」frontendで表示 |
| baseStats | Object | 種族値。下記6項目を表示 |
| hp | int | Hp 棒グラフとして表示 |
| attack | number | こうげき 棒グラフとして表示 |
| defense | number | ぼうぎょ 棒グラフとして表示 |
| specialAttack | number | とくこう 棒グラフとして表示 |
| specialDefense | number | とくぼう 棒グラフとして表示 |
| speed | number | すばやさ 棒グラフとして表示 |

## ステータスコード

| コード | 条件 |
|---|---|
| 200 | 正常終了 |
| 204 | 該当なし |
| 400 | boxIdが整数でない/「入力内容を確認してください」 |
| 401 | 未認証 |

## 備考

- 画像も併せて表示する

