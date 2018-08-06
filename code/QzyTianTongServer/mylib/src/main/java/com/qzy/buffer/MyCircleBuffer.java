package com.qzy.buffer;

/**
 * Created by yj.zhang on 2018/7/11/011.
 */

public class MyCircleBuffer {
    public Integer nextg;//下一个可用的缓冲区,即其中的数据可以被进程使用
    public Integer nexti;//下一个空的缓冲区
    public Integer current;//当前正在使用的缓冲区
    public int maxsize;//缓冲区的个数
    public MyBuffer[] mbArray;

    public MyCircleBuffer(int maxsize) {
        super();
        mbArray = new MyBuffer[maxsize];//将缓冲区初始化
        for(int i=0;i<maxsize;i++){
            MyBuffer mb = new MyBuffer();
            mb.rank = i;
            mb.data=null;
            mbArray[i] = mb;
        }
        this.maxsize = maxsize;
        this.current=0;
        this.nexti=0;
        this.nextg = 0;//
    }
    /**
     * 选取下一个空缓冲区存放内容
     * @throws Exception
     */
    public boolean putBuf(byte[] o) throws Exception{
        if(this.nexti==null){
            throw new Exception("所有缓冲区已满,不能在存放数据");
        }
        mbArray[this.nexti].data = new byte[o.length];//存放的内容
        System.arraycopy(o,0,mbArray[this.nexti].data,0,o.length);
        mbArray[this.nexti].status = 1;//将该缓冲区修改成当前的数据可以被访问
        //如果nextg的值为null,说明在放入该数据之前原缓冲区数组为空,将执行this.nexti = this.nextg
        if(this.nextg==null){
            this.nextg = this.nexti;
        }
        //从该空缓冲区向后检索，直到发现第一个空缓冲区退出该函数
        for(int i=this.nexti;i<this.maxsize;i++){
            if(mbArray[i].status==0){
                this.nexti = i;
                return true;
            }
        }
        //如果该缓冲区向后无缓冲区,则从缓冲区数组最开始进行检索
        for(int i=0;i<this.nexti;i++){
            if(mbArray[i].status==0){
                this.nexti = i;
                return true;
            }
        }
        this.nexti = null;
        return false;
    }
    /**
     * 从nexti指向的缓冲区中获取数据
     * @return
     * @throws Exception
     */
    public boolean getBuf() throws Exception{
        if(this.nextg==null){
            throw new Exception("没有可以获取数据的缓冲区，内容获取失败");
        }
        if(this.mbArray[this.nextg].data==null){
            throw new Exception("该缓冲区没有内容，内容获取失败");
        }
        this.mbArray[this.nextg].status = 2;//要提取该缓冲区的数据时,将该缓冲区的状态设置为2
        this.current = this.nextg;//让当前工作的缓存指针指向nexti
        System.out.println("现在将  "+this.mbArray[this.current].rank+"号缓冲区内的内容:   "+this.mbArray[this.nexti].data+"交给进程进行处理");
        System.out.println("当前正在提取数据的缓冲区是: "+this.mbArray[this.current].rank);
        releaseBuf(this.current);
        for(int i=this.nextg;i<this.maxsize;i++){
            if(mbArray[i].status==1){
                this.nextg = i;
                return true;
            }
        }
        for(int i=0;i<this.nextg;i++){
            if(mbArray[i].status==1){
                this.nextg = i;
                return true;
            }
        }
        this.nextg=null;
        return false;
    }
    /**
     * 将指定的缓冲区的内容设置为null
     * @param current
     * @return
     */
    public boolean releaseBuf(Integer current){
        this.mbArray[current].data =null;
        this.mbArray[current].status = 0;
        this.nexti = this.current;//当前缓冲区设置为下一个可以使用的空缓冲区
        return true;
    }
    @Override
    public String toString() {
        return "下一个可用缓冲区G是: "+nextg+" \n"
                +"下次可用的空缓冲区R是: "+nexti+" \n"
                +"指示计算进程正在使用的缓冲区C的指针: "+current;
    }


}
