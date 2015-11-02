#include "susan.sh"
//import "c_uchar7220_queue";
import "c_uchar7220_queue_both_SR";
import "HWBus";
import "OS";
import "susan";
channel master_send_driver(IMasterHardwareBus myHardwareBus, unsigned bit[ADDR_WIDTH-1:0] addr) implements i_uchar7220_sender{
    unsigned long mySize =  IMAGE_SIZE;
    void send(uchar7220 outImage){
        myHardwareBus.MasterSyncReceive2();
        myHardwareBus.MasterWrite(addr, outImage, mySize);
    }
};

channel master_receive_driver (IMasterHardwareBus myHardwareBus, unsigned bit[ADDR_WIDTH-1:0] addr) implements i_uchar7220_receiver {
    unsigned long mySize =  IMAGE_SIZE;
    void receive(uchar7220 *inImage){
        myHardwareBus.MasterSyncReceive();
        myHardwareBus.MasterRead(addr, inImage, mySize);
    }
};

behavior susan_driver_wrapper(IMasterHardwareBus  myHardwareBus)
{
    OS myOS;
    unsigned bit[ADDR_WIDTH-1:0] addr1 = 1;
    unsigned bit[ADDR_WIDTH-1:0] addr2 = 2;
    master_receive_driver myMaster_receive_driver(myHardwareBus,addr1);
    master_send_driver myMaster_send_driver(myHardwareBus,addr2);
    Susan TASK_PE1(myMaster_receive_driver, myMaster_send_driver, myOS);
    
    void main (void) {
        fsm { 
            TASK_PE1: goto TASK_PE1;
        } 
    }
};



