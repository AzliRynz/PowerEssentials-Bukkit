<?php
function convertSpacesToTabs($directory) {
    $files = new RecursiveIteratorIterator(new RecursiveDirectoryIterator($directory));

    foreach ($files as $file) {
        if ($file->isFile() && pathinfo($file, PATHINFO_EXTENSION) === "java") {
            $filePath = $file->getPathname();
            $content = file_get_contents($filePath);
            $content = str_replace("    ", "\t", $content); // Ubah 4 spasi ke tab
            file_put_contents($filePath, $content);
            echo "Converted: $filePath\n";
        }
    }
}

$targetDir = $argv[1] ?? getcwd(); // Gunakan argumen atau folder saat ini
convertSpacesToTabs($targetDir);
?>
