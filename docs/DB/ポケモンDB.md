# DB設計書

## ポケモンテーブル

### 基本情報

| 項目 | 内容 |
|---|---|
| テーブル英名 | pokemon |
| テーブル和名 | ポケモン
| 備考 | ポケモンの情報を管理する |

### カラム情報

| 英名 | 和名 | 型 | 必須 | PK/FK | 備考 |
|---|---|---|---|---|---|
| pokemon_id | 図鑑番号 | int | o | PK | ポケモンの番号 |
| name | 名前 | varchar(32) | o |  | ポケモンの名前 |
| gender | 性別 | int | o |  | 1:オスのみ, 2:メスのみ, 3:不明, 4:オス・メス両方 |
| type1_id | タイプ1ID | int | o | FK | １つ目のタイプを識別 |
| type2_id | タイプ2ID | int | o | FK | ２つ目のタイプを識別 |
| category | 分類 | varchar(32) | o |  | ポケモンの分類 |
| height | 高さ | numeric(4,1) | o |  | 単位は「m」frontendで表示 |
| weight | 重さ | numeric(4,1) | o |  | 単位は「kg」frontendで表示 |
| base_hp | HP | int | o |  | 種族値。棒グラフとして表示 |
| base_attack | こうげき | int | o |  | 種族値。棒グラフとして表示 |
| base_defense | ぼうぎょ | int | o |  | 種族値。棒グラフとして表示 |
| bace_special_attack | とくこう | int | o |  | 種族値。棒グラフとして表示 |
| base_special_defense | とくぼう | int | o |  | 種族値。棒グラフとして表示 |
| bace_speed | すばやさ | int | o |  | 種族値。棒グラフとして表示 |

| xx | xx | xx | "o" または "x" | "PK" または "FK" | xx |

