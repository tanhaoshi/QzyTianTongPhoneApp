#!/system/bin/sh
#备份so
SRC_PATH=/system/lib
OUT_PATH=/mnt/sdcard/update/backup/lib
if [  -d $OUT_PATH ]
then
	mkdir $OUT_PATH
fi

if [  -f $SRC_PATH/libnetled.so ]
then
	cp $SRC_PATH/libnetled.so $OUT_PATH/libnetled.so 
fi

if [  -f $SRC_PATH/libttswitch.so ]
then
	cp $SRC_PATH/libttswitch.so $OUT_PATH/libttswitch.so 
fi

if [  -f $SRC_PATH/libQzy0.so ]
then
	cp $SRC_PATH/libQzy0.so $OUT_PATH/libQzy0.so 
fi

if [  -f $SRC_PATH/libQzy1.so ]
then
	cp $SRC_PATH/libQzy1.so $OUT_PATH/libQzy1.so
fi





#备份apk
SRC_PATH=/system/app
OUT_PATH=/mnt/sdcard/update/backup/server_apk
if [  -d $OUT_PATH ]
then
	mkdir $OUT_PATH
fi

if [  -f $SRC_PATH/qzytt_server/qzytt_server.apk ]
then
	cp $SRC_PATH/qzytt_server/qzytt_server.apk $OUT_PATH/qzytt_server.apk
fi

#备份bin
#备份bin
SRC_PATH=/system/bin
OUT_PATH=/mnt/sdcard/update/backup/server_bin
if [  -d $OUT_PATH ]
then
	mkdir $OUT_PATH
fi
if [  -f $SRC_PATH/pcm_service ]
then
	cp $SRC_PATH/pcm_service $OUT_PATH/pcm_service
fi

