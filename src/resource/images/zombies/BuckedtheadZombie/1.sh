#!/bin/bash

# 检查文件是否存在
for i in {1..19}; do
    if [ ! -f "zombie$i.png" ]; then
        echo "文件 zombie$i.png 不存在，请检查当前目录。"
        exit 1
    fi
done

# 重命名文件
for i in {10..19}; do
    new_num=$((i - 10))
    mv "zombie$i.png" "ConeheadZombie$new_num.png"
    echo "已将 zombie$i.png 重命名为 ConeheadZombie$new_num.png"
done

echo "所有文件重命名完成。"
