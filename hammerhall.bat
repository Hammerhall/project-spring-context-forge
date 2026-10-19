@echo off
REM ============================================================
REM  Hammerhall CLI — обёртка-загрузчик для Windows.
REM  Логика та же, что в hammerhall: скачать при первом запуске,
REM  дальше просто запускать.
REM
REM  ── Как приезжает обновление ────────────────────────────────
REM  Версия закреплена ЗДЕСЬ и входит в имя файла. Подняли версию —
REM  обёртка не находит jar и качает новый, то есть обновление
REM  доезжает вместе с `git pull` кузницы.
REM
REM  НИКОГДА не перевыкладывайте один номер версии с другим
REM  содержимым: имя файла то же, обёртка считает клиент скачанным,
REM  и человек навсегда остаётся со старым.
REM
REM  `hammerhall update` — принудительно перекачать. Перехватывается
REM  ДО запуска java: на Windows работающий jar заблокирован, и сам
REM  себя клиент заменить не может.
REM
REM  ⚠️ EnableDelayedExpansion обязателен. Раньше URL собирался внутри
REM  блока if (...) через %URL%, а cmd подставляет переменные при
REM  разборе ВСЕГО блока — то есть до того, как set выполнится.
REM  В powershell уезжал пустой адрес, и загрузка молча не работала.
REM  Внутри блоков переменные читаются только как !URL!.
REM
REM  ── Кодировка консоли ───────────────────────────────────────
REM  chcp 65001 переводит консоль в UTF-8. Клиент и без этого читаем:
REM  привязанный к консоли, он кодирует вывод её страницей сам. Но
REM  в cp866 нет ни длинного тире, ни значков ✓ ⚠ ✗ — они станут
REM  вопросительными знаками. С UTF-8 текст выходит полностью.
REM
REM  Страница меняется только в этом окне и только на время команды:
REM  прежняя запоминается и возвращается после запуска.
REM ============================================================
setlocal EnableDelayedExpansion

set VERSION=0.1.4
set REPO=hammerhall/hammerhall-cli

set ROOT=%~dp0
set DIR=%ROOT%.hammerhall
set JAR=%DIR%\cli-%VERSION%.jar
set URL=https://github.com/%REPO%/releases/download/v%VERSION%/cli-%VERSION%.jar

where java >nul 2>nul
if errorlevel 1 (
    echo Нужна Java. Она же нужна, чтобы решать задачи — поставьте JDK 25.
    exit /b 1
)

if /i "%~1"=="update" (
    if exist "%DIR%" del /q "%DIR%\cli-*.jar" 2>nul
    if exist "%DIR%" del /q "%DIR%\*.part" 2>nul
    call :download
    if errorlevel 1 exit /b 1
    echo Клиент обновлён до %VERSION%.
    exit /b 0
)

if not exist "%JAR%" (
    call :download
    if errorlevel 1 exit /b 1
)

REM Запомнить страницу консоли и вернуть её после запуска. Номер берём
REM из второго поля вывода chcp: текст вокруг переведён, число — нет.
set PAGE=
for /f "tokens=2 delims=:" %%p in ('chcp') do set PAGE=%%p
set PAGE=%PAGE: =%

chcp 65001 >nul
java -jar "%JAR%" %*
set CODE=%errorlevel%
if not "%PAGE%"=="" chcp %PAGE% >nul

exit /b %CODE%

:download
echo Загружаю клиент %VERSION%...
if not exist "%DIR%" mkdir "%DIR%"
REM Во временный файл, потом переименование: оборвётся связь на середине —
REM останется мусор с временным именем, а не битый jar под правильным.
powershell -NoProfile -Command "Invoke-WebRequest -Uri '%URL%' -OutFile '%JAR%.part'"
if errorlevel 1 (
    echo Не удалось скачать клиент. Проверьте связь.
    exit /b 1
)
move /y "%JAR%.part" "%JAR%" >nul
REM Прошлые версии больше не нужны: обёртка запускает только свою.
for %%f in ("%DIR%\cli-*.jar") do (
    if /i not "%%~nxf"=="cli-%VERSION%.jar" del /q "%%f" >nul 2>nul
)
exit /b 0
