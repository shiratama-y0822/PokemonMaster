# DB設計書

## パーティテーブル

### 基本情報

| 項目 | 内容 |
|---|---|
| テーブル英名 | parties |
| テーブル和名 | パーティ |
| 備考 | パーティの情報を管理する |

### カラム情報

| 英名 | 和名 | 型 | 必須 | PK/FK | 備考 |
|---|---|---|---|---|---|
| party_id | パーティID | serial | o | PK | パーティを識別 |
| user_id | ユーザーID | varchar（16） | o | FK | 作成したユーザー |
| party_name | パーティネーム | varchar(16) | o |  | パーティ名 |
| position1 | 配置1 | serial | x | FK(boxes.box_id) | パーティの配置1 |
| position2 | 配置2 | serial | x | FK(boxes.box_id) | パーティの配置2 |
| position3 | 配置3 | serial | x | FK(boxes.box_id) | パーティの配置3 |
| position4 | 配置4 | serial | x | FK(boxes.box_id) | パーティの配置4 |
| position5 | 配置5 | serial | x | FK(boxes.box_id) | パーティの配置5 |
| position6 | 配置6 | serial | x | FK(boxes.box_id) | パーティの配置6 |
     