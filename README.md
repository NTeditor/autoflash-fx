Переписаный [auto-flash-for-rmx3834](https://github.com/NTeditor/auto-flash-for-rmx3834) на Java.

# Запуск
## Требования
- **Android Platform Tools** (должно быть добавлено в `PATH`).

## Windows
1. Распакуйте архив `autoflash-fx-0.0.1-SNAPSHOT.zip`.
2. Перейдите в папку `bin`.
3. Запустите файл `autoflash-fx-launcher.bat`.

## Linux
Не тестировалось


# Сборка
## Требования
- **Java 21** или выше.

## Windows
```batch
./mvnw.bat clean javafx:jlink
```

## Linux
```bash
./mvnw clean javafx:jlink
```
