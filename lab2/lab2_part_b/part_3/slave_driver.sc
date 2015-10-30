#include "susan.sh"
import "c_uchar7220_queue_both_SR";
import "HWBus";
import "OS";
import "read_image";
import "write_image";
channel slave_send_driver(ISlaveHardwareBus myHardwareBus, unsigned bit[ADDR_WIDTH-1:0] addr) implements i_uchar7220_sender {
    unsigned long mySize =  IMAGE_SIZE;
    void send(uchar7220 outImage){
        printf("--+++-----\n");
        myHardwareBus.SlaveSyncSend();
        //printf("done with syncing in slave\n");
        myHardwareBus.SlaveSyncSend();
        printf("++00111111\n");
        myHardwareBus.SlaveWrite(addr, outImage, mySize);
    }
};

channel slave_receive_driver(ISlaveHardwareBus myHardwareBus, unsigned bit[ADDR_WIDTH-1:0] addr) implements i_uchar7220_receiver {
    unsigned long mySize =  IMAGE_SIZE;
    void receive(uchar7220 *outImage){
        myHardwareBus.SlaveSyncSend2();
        //printf("--0--1-1-1-1n"); 
        //printf("done with syncing2 in slave\n");
        myHardwareBus.SlaveRead(addr, outImage, mySize);
        //printf("====================\n");
    }
};

behavior readImage_driver_wrapper(ISlaveHardwareBus myHardwareBus, in uchar image_buffer[IMAGE_SIZE], i_receive start)
{
    unsigned bit[ADDR_WIDTH-1:0] addr = 1; 
    slave_send_driver mySlave_send_driver(myHardwareBus,addr);
    ReadImage myRead_image(start, image_buffer, mySlave_send_driver);
    
    void main (void) {
           //printf(" in readimage wrapper\n"); 
           //fsm{ 
           // myRead_image: goto myRead_image;
            
            myRead_image.main();
        
        //} 
    }
};

behavior writeImage_driver_wrapper(ISlaveHardwareBus myHardwareBus, i_sender out_image_susan)
{
    
    unsigned bit[ADDR_WIDTH-1:0] addr = 2; 
    slave_receive_driver mySlave_receive_driver(myHardwareBus, addr);
    WriteImage myWrite_image(mySlave_receive_driver, out_image_susan);
    
    void main (void) {
            fsm { 
            myWrite_image: goto myWrite_image;
            }
        
    }
};
