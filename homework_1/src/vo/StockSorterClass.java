package vo;


public class StockSorterClass implements interfaces.StockSorter{
    public static int partition(StockInfo[] array,int lo ,int hi){
        //固定的切分方式
        StockInfo key;
        key=array[lo];
        while(lo<hi){
            while(array[hi].ANSWER.length()>=key.ANSWER.length()&&hi>lo){//从后半部分向前扫描
                hi--;
            }
            array[lo]=array[hi];
            while(array[lo].ANSWER.length()<=key.ANSWER.length()&&hi>lo){//从前半部分向后扫描
                lo++;
            }
            array[hi]=array[lo];
        }
        array[hi]=key;
        return hi;
    }
    public StockInfo[] sort(StockInfo[] array,int lo,int hi){
        if(lo>=hi){
            return array;
        }
        int index=partition(array,lo,hi);
        sort(array,lo,index-1);
        sort(array,index+1,hi);
        return array;
    }
    public StockInfo[] sort(StockInfo[] info,boolean order){

        info = sort(info,0,info.length-1);//升序快速排序
        if (order==false){//如果选择降序则先升序排序然后把行颠倒
            StockInfo temp;
            for(int i=0;i<info.length/2;i++){
                temp=info[i];
                info[i]=info[info.length-1-i];
                info[info.length-1-i]=temp;
            }
        }

        return  info;
    }


}

