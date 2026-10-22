# DB設計書

## タイプ効果テーブル

### 基本情報

| 項目 | 内容 |
|---|---|
| テーブル英名 | typeEffect |
| テーブル和名 | タイプ相性 |
| 備考 | 攻撃・防御のタイプ相性倍率を管理する |

### カラム情報

| 英名 | 和名 | 型 | 必須 | PK/FK | 備考 |
|---|---|---|---|---|---|
| attackTypeId | 攻撃タイプID | int | o | PK | types.typeIdを参照 |
| defenceTypeId | 防御タイプID | int | o | PK | types.typeIdを参照 |
| multiplier | ダメージ倍率 | NUMERIC(3,1) | o |  | 0, 0.5, 1, 2 |
