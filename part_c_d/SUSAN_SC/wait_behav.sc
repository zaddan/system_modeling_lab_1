#include <stdio.h>
import "c_double_handshake";
behavior wait_behav(void){
    void main(void) {
    }
};

behavior wait_print(in int crap){
    void main(void) {
           
           // printf("inside %d\n", crap);
    }
};

behavior wait_recv(i_receiver waitCondition){
    int flagValue; 
    void main(void) {
        waitCondition.receive(&flagValue, 1);
    }
};

