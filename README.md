# Chess Analyzer Android — Phone Project

Project Android Studio Kotlin dành cho điện thoại Android.

## Có sẵn
- Nút menu nổi `☰` hiển thị trên ứng dụng khác.
- MediaProjection để chụp màn hình.
- Foreground Service tương thích Android mới.
- Engine phân tích offline trong app.
- Nút bật / dừng toàn bộ.
- Không cần server.

## Cách tạo APK
1. Cài Android Studio trên máy tính.
2. Mở **thư mục `ChessAnalyzerAndroid`**.
3. Chờ Gradle Sync hoàn tất.
4. Chọn **Build > Build APK(s)**.
5. APK debug nằm tại:
   `app/build/outputs/apk/debug/app-debug.apk`
6. Chép APK vào điện thoại và cài đặt.

## Trên điện thoại
Mở app → Bật nút menu nổi → cấp quyền **Hiển thị trên ứng dụng khác**.
Sau đó bấm **Bật tự phân tích màn hình** → chấp nhận quyền chụp màn hình của Android.

## Quan trọng
Project này đã có pipeline chụp màn hình và phân tích, nhưng phần nhận diện quân cờ chưa thể đoán chính xác mọi game cờ khác nhau. Mỗi game có giao diện/skin bàn cờ riêng. Muốn tự nhận diện chính xác cần thêm template/model cho game mục tiêu.

## Cấu trúc
- `app/src/main/java/com/chessanalyzer/MainActivity.kt` — màn hình chính.
- `OverlayService.kt` — nút nổi + bảng kết quả.
- `CaptureService.kt` — chụp màn hình.
- `Analyzer.kt` — nhận diện + engine.
- `AndroidManifest.xml` — quyền và service.


## Build APK directly with GitHub Actions

This project includes `.github/workflows/build-apk.yml`.

1. Upload/push the project to a GitHub repository.
2. Open **Actions** → **Build Android APK**.
3. Choose **Run workflow**.
4. Wait for the build to finish.
5. Open the completed workflow run.
6. Under **Artifacts**, download `chess-analyzer-debug-apk`.
7. Extract the downloaded ZIP on your phone and install the `.apk`.

The workflow builds a debug APK with Gradle and uploads it as a GitHub Actions artifact.
