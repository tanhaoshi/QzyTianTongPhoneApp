#!bin/sh

#恢复so
SRC_PATH = /mnt/sdcard/update/backup/lib 
OUT_PATH = /system/lib

if [  -f "$SRC_PATH/libnetled.so" ]; then
	cp $SRC_PATH/libnetled.so $OUT_PATH/libnetled.so 
fi

if [  -f "$SRC_PATH/libttswitch.so" ]; then
	cp $SRC_PATH/libttswitch.so $OUT_PATH/libttswitch.so 
fi

if [  -f "$SRC_PATH/libQzy0.so" ]; then
	cp $SRC_PATH/libQzy0.so $OUT_PATH/libQzy0.so 
fi

if [  -f "$SRC_PATH/libQzy1.so" ]; then
	cp $SRC_PATH/libQzy1.so $OUT_PATH/libQzy1.so
fi

#恢复apk
SRC_PATH = /mnt/sdcard/update/backup/server_apk
OUT_PATH = /system/app
if [  -f "$SRC_PATH/qzytt_server/qzytt_server.apk" ]; then
	cp $SRC_PATH/qzytt_server/qzytt_server.apk $OUT_PATH/qzytt_server.apk

fi

#恢复bin
SRC_PATH = /mnt/sdcard/update/backup/server_bin
OUT_PATH = /system/bin
if [  -f "$SRC_PATH/pcm_service" ]; then
	cp $SRC_PATH/pcm_service $OUT_PATH/pcm_service

fi