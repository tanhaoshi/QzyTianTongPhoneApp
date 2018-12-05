#!/system/bin/sh


function test_return(){
    ret=$1
    if [ $ret -ne 0 ]; then
        echo "backup error return ${ret}"
        exit $ret
    fi
}

function def_cp(){
    if [ -f $1 ]; then
        cp $1 $2
        test_return $?
    fi
}

function def_cp_so(){
    if [ ! -d $2 ]; then
        mkdir -p $2
    fi
    def_cp $1/libnetled.so $2/libnetled.so
    def_cp $1/libttswitch.so $2/libttswitch.so
    def_cp $1/libQzy0.so $2/libQzy0.so
    def_cp $1/libQzy1.so $2/libQzy1.so

    chmod 644 $2/libnetled.so
    chmod 644 $2/libttswitch.so
    chmod 644 $2/libQzy0.so
    chmod 644 $2/libQzy1.so

}

function def_cp_apk(){
    if [  ! -d $2 ]; then
        mkdir -p $2
    fi
    def_cp $1/qzytt_server.apk $2/qzytt_server/qzytt_server.apk
    chmod 644 $2/qzytt_server/qzytt_server.apk

}

function def_cp_bin(){
    if [  ! -d $2 ]; then
        mkdir -p $2
    fi
    chmod 777 $1/pcm_service
    def_cp $1/pcm_service $2/pcm_service
}

#backup
function backup(){

    if [  ! -d "/mnt/sdcard/update/backup/" ]; then
        mkdir -p "/mnt/sdcard/update/backup/" 
    fi


#备份so
    def_cp_so /system/lib /mnt/sdcard/update/backup/lib
#备份apk
    def_cp_apk /system/app /mnt/sdcard/update/backup/server_apk
#备份bin
    def_cp_bin /system/bin /mnt/sdcard/update/backup/server_bin
}


#backup
function recovery(){
    mount -o remount /system
    test_return $?
    sync
    sync
#备份so
    def_cp_so  /mnt/sdcard/update/backup/lib /system/lib
#备份apk
    def_cp_apk /mnt/sdcard/update/backup/server_apk /system/app
#备份bin
    def_cp_bin /mnt/sdcard/update/backup/server_bin /system/bin
}

function update(){
    mount -o remount /system
    test_return $?
    sync
    sync
    def_cp_so /mnt/sdcard/update/lib /system/lib
    def_cp_apk /mnt/sdcard/update/server_apk /system/app
    def_cp_bin /mnt/sdcard/update/server_bin /system/bin
}

if [ $1 -eq 1 ];then
    echo "do backup"
    backup
elif [ $1 -eq 2 ];then
    echo "do recovery"
    recovery
elif [ $1 -eq 3 ]; then
    echo "do upadte"
    update
fi
exit 0
