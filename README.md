# hello-openapi

OpenAPI コントラクトファーストで構築した Spring Boot アプリケーション。

## API ドキュメント

https://va034600.github.io/hello-openapi/

GitHub Pages で Swagger UI をホスティングしています。

## 前提条件

- JDK 17+

## ビルド

```bash
./gradlew clean build
```

ビルド時に `src/main/resources/openapi/openapi.yaml` から `HelloApi` インターフェースが自動生成される。

## 起動

```bash
./gradlew bootRun
```

## 動作確認

```bash
# GET /hello
curl http://localhost:8080/hello
# Hello, World!

# POST /hello2（name: 必須、最大256文字）
curl -X POST http://localhost:8080/hello2 \
  -H 'Content-Type: application/json' \
  -d '{"name": "Alice"}'
# Hello, Alice!
```

## OpenAPI YAML の使い方

API の定義は `src/main/resources/openapi/openapi.yaml` に記述する。ビルド時に [openapi-generator](https://github.com/OpenAPITools/openapi-generator) がこの YAML からコントローラーインターフェース (`HelloApi`) とモデルクラスを自動生成する。

### エンドポイントの追加手順

1. `openapi.yaml` の `paths` にエンドポイントを追加する（タグは `Hello` を指定）
2. リクエスト/レスポンスにオブジェクトが必要な場合は `components/schemas` にスキーマを定義する
3. `./gradlew clean build` でコードを再生成する
4. `HelloController` に生成されたメソッドを `override` して実装する

### 現在の定義例

```yaml
paths:
  /hello:          # GET  - パラメータなし、text/plain を返す
  /hello2:         # POST - Hello2Request（name: 必須、最大256文字）を受け取る
```

### 生成設定（build.gradle.kts）

| 設定 | 値 | 説明 |
|---|---|---|
| `generatorName` | `kotlin-spring` | Kotlin + Spring Boot 向けコード生成 |
| `interfaceOnly` | `true` | インターフェースのみ生成（実装は手書き） |
| `useSpringBoot3` | `true` | Jakarta 名前空間を使用 |
| `useTags` | `true` | タグ名からインターフェース名を決定 |
| `documentationProvider` | `none` | Swagger UI 依存を除外 |

生成コードは `build/generated/openapi/` に出力され、`sourceSets` で自動的にコンパイル対象に含まれる。

## プロジェクト構成

```
src/main/resources/openapi/openapi.yaml  # API定義（単一ソース）
src/main/kotlin/com/example/             # アプリケーションコード
build/generated/openapi/                 # 生成コード（ビルド時に作成）
```
