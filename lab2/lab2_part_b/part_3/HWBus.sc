//////////////////////////////////////////////////////////////////////
// File:   	HWBus.sc
//////////////////////////////////////////////////////////////////////

//import "i_send";
//import "i_receive";
//import "i_transceive";

import "c_double_handshake";
import "c_handshake";

#include "susan.sh"
// Simple hardware bus

#define DATA_WIDTH	32u

#if DATA_WIDTH == 32u
# define DATA_BYTES 4u
#elif DATA_WIDTH == 16u
# define DATA_BYTES 2u
#elif DATA_WIDTH == 8u
# define DATA_BYTES 1u
#else
# error "Invalid data width"
#endif


/* ----- Physical layer, bus protocol ----- */

// Protocol primitives
interface IMasterHardwareBusProtocol
{
  void masterWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d);
  void masterRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d);
};


interface ISlaveHardwareBusProtocol
{
  void slaveWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d);
  void slaveRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d);
};

// Master protocol implementation
channel MasterHardwareBus(out signal unsigned bit[ADDR_WIDTH-1:0] A,
                              signal unsigned bit[DATA_WIDTH-1:0] D,
                          out signal unsigned bit[1]    ready,
                          in  signal unsigned bit[1]    ack)
  
  implements IMasterHardwareBusProtocol
{
  void masterWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d)
  {
    do {
      t1: A = a;
          D = d;
          waitfor(5000);
      t2: ready = 1;
          while(!ack) wait(ack);
      t3: waitfor(10000);
      t4: ready = 0;
          while(ack) wait(ack);
    }
    timing {
      range(t1; t2; 5000; 15000);
      range(t3; t4; 10000; 25000);
    }
  }

  void masterRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d)
  {
    do {
      t1: A = a;
          waitfor(5000);
      t2: ready = 1;
          while(!ack) wait(ack);
      t3: *d = D;
          waitfor(15000);
      t4: ready = 0;
          while(ack) wait(ack);
    }
    timing {
      range(t1; t2; 5000; 15000);
      range(t3; t4; 10000; 25000);
    }
  }

};


channel HardwareBusProtocolTLM(inout signal unsigned bit[ADDR_WIDTH-1:0] A,signal unsigned bit[DATA_WIDTH-1:0] D,event TLMEvent1, event TLMEvent2)
//channel HardwareBusProtocolTLM(inout signal unsigned bit[ADDR_WIDTH-1:0] A,signal unsigned bit[DATA_WIDTH-1:0] D)
  implements IMasterHardwareBusProtocol, ISlaveHardwareBusProtocol
{
  
//  c_handshake TLMEvent1;
//  c_handshake TLMEvent2;
  void masterWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d)
  {
      A = a;
      D = d;
      notify(TLMEvent1);
      waitfor(20000); 
   
   }
   
  void masterRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d)
  {
    //printf("before_masterReadprotocol %llu \n",now()); 
    wait(TLMEvent2);
    //printf("masterReadprotocol_after_wait\n"); 
    A = a;
    *d = D;
    waitfor(20000); 
  }

  void slaveWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d)
  {
      //while(A!=a){}
      D = d;
      notify(TLMEvent2); 
      //printf("slavewriteprotocol_after_notify %llu\n",now()); 
      waitfor(20000); 
  } 
  void slaveRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d)
  {
     // while(a!=A);
      wait(TLMEvent1); 
      *d = D;
      waitfor(20000); 
  }
};


// Slave protocol implementation
channel SlaveHardwareBus(in  signal unsigned bit[ADDR_WIDTH-1:0] A,
                             signal unsigned bit[DATA_WIDTH-1:0] D,
                         in  signal unsigned bit[1]    ready,
                         out signal unsigned bit[1]    ack)
  implements ISlaveHardwareBusProtocol
{
  void slaveWrite(unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] d)
  {
    do {
      t1: while(!ready) wait(ready);
      t2: if(a != A) {
            waitfor(1000); // avoid hanging from t2 to t1
            goto t1;
          }
          else {
            D = d;
            waitfor(12000);
          }
      t3: ack = 1;
          while(ready) wait(ready);
      t4: waitfor(7000);
      t5: ack = 0;
    }
    timing {
      range(t2; t3; 10000; 20000);
      range(t4; t5; 5000; 15000);
    }
  }

  void slaveRead (unsigned bit[ADDR_WIDTH-1:0] a, unsigned bit[DATA_WIDTH-1:0] *d)
  {
    do {
      t1: while(!ready) wait(ready);
      t2: if(a != A) {
            waitfor(1000);  // avoid hanging from t2 to t1
	    goto t1;
	  }
          else {
            *d = D;
            waitfor(12000);
          }
      t3: ack = 1;
          while(ready) wait(ready);
      t4: waitfor(7000);
      t5: ack = 0;
    }
    timing {
      range(t2; t3; 10000; 20000);
      range(t4; t5; 5000; 15000);
    }
  }
};

/* -----  Physical layer, interrupt handling ----- */

//channel MasterHardwareSyncDetect(in signal unsigned bit[1] intr, i_)
//channel MasterHardwareSyncDetect(unsigned bit[1] intr)
// implements i_receive
//{
// void receive(void)
//  {
//    wait(rising intr);
//  }
//
//};
//
//channel SlaveHardwareSyncGenerate(out signal unsigned bit[1] intr)
//  implements i_send
//{
//  void send(void)
//  {
//    intr = 1;
//    waitfor(5000);
//    intr = 0;
//  }
//};
//
//
/* -----  Media access layer ----- */

interface IMasterHardwareBusLinkAccess
{
  void MasterRead(int addr, void *data, unsigned long len);
  void MasterWrite(int addr, const void* data, unsigned long len);
};
  
interface ISlaveHardwareBusLinkAccess
{
  void SlaveRead(int addr, void *data, unsigned long len);
  void SlaveWrite(int addr, const void* data, unsigned long len);
};

channel MasterHardwareBusLinkAccess(IMasterHardwareBusProtocol protocol)
  implements IMasterHardwareBusLinkAccess
{
  void MasterWrite(int addr, const void* data, unsigned long len)
  {    
    unsigned long i;
    unsigned char *p;
    unsigned bit[DATA_WIDTH-1:0] word = 0;
   
    for(p = (unsigned char*)data, i = 0; i < len; i++, p++)
    {
      word = (word<<8) + *p;
      
      if(!((i+1)%DATA_BYTES)) {
	 protocol.masterWrite(addr, word);
	 word = 0;
      }
    }
    
    if(i%DATA_BYTES) {
      word <<= 8 * (DATA_BYTES - (i%DATA_BYTES));
      protocol.masterWrite(addr, word);
    }    
  
    //printf("888888^^^^^^^^^^\n"); 
  
  }
  
  void MasterRead(int addr, void* data, unsigned long len)
  {
    unsigned long i;
    unsigned char* p;
    unsigned bit[DATA_WIDTH-1:0] word;
   
    printf("in MasterRead_before _Read\n"); 
    for(p = (unsigned char*)data, i = 0; i < len; i++, p++)
    {
      if(!(i%DATA_BYTES)) {
	protocol.masterRead(addr, &word);
      }

      *p = word[DATA_WIDTH-1:DATA_WIDTH-8];
      word = word << 8;      
    }
    printf("in masterRead_after_wait\n"); 
  }
};

channel SlaveHardwareBusLinkAccess(ISlaveHardwareBusProtocol protocol)
  implements ISlaveHardwareBusLinkAccess
{
  void SlaveWrite(int addr, const void* data, unsigned long len)
  {    
    unsigned long i;
    unsigned char *p;
    unsigned bit[DATA_WIDTH-1:0] word = 0;
   
    //printf("in slaveWrite\n"); 
    for(p = (unsigned char*)data, i = 0; i < len; i++, p++)
    {
      word = (word<<8) + *p;
      
      if(!((i+1)%DATA_BYTES)) {
	protocol.slaveWrite(addr, word);
	word = 0;
      }
    }
    
    if(i%DATA_BYTES) {
      word <<= 8 * (DATA_BYTES - (i%DATA_BYTES));
      protocol.slaveWrite(addr, word);
    }    
  
    //printf("in slaveWrite_after_notify\n"); 
    //printf("888888^^^^^^^^^^\n"); 
  }
  
  void SlaveRead(int addr, void* data, unsigned long len)
  {
    unsigned long i;
    unsigned char* p;
    unsigned bit[DATA_WIDTH-1:0] word;
   
    for(p = (unsigned char*)data, i = 0; i < len; i++, p++)
    {
      if(!(i%DATA_BYTES)) {
	protocol.slaveRead(addr, &word);
      }

      *p = word[DATA_WIDTH-1:DATA_WIDTH-8];
      word = word << 8;      
    }
  }
};


/* -----  Bus instantiation example ----- */

// Bus protocol interfaces
interface IMasterHardwareBus
{
  void MasterRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len);
  void MasterWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void* data, unsigned long len);
  
  void MasterSyncReceive();
  void MasterSyncReceive2();
  //void MasterSyncSend();
};
  
interface ISlaveHardwareBus
{
  void SlaveRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len);
  void SlaveWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void* data, unsigned long len);
  
  void SlaveSyncSend();
  void SlaveSyncSend2();
  //void SlaveSyncReceive();
};


// Bus protocol channel
channel HardwareBus()
  implements IMasterHardwareBus, ISlaveHardwareBus
{
  // wires
  signal unsigned bit[ADDR_WIDTH-1:0] A;
  signal unsigned bit[DATA_WIDTH-1:0] D;
  signal unsigned bit[1]    ready = 0;
  signal unsigned bit[1]    ack = 0;
  event TLMEvent1;
  event TLMEvent2;
  

// interrupts
  signal unsigned bit[1]    int0 = 0;
  signal unsigned bit[1]    int1 = 0;

//  MasterHardwareSyncDetect  MasterSync0(int0);
//  SlaveHardwareSyncGenerate SlaveSync0(int0);

//  MasterHardwareSyncDetect  MasterSync1(int1);
//  SlaveHardwareSyncGenerate SlaveSync1(int1);
//  



  //c_handshake SlaveSync2;
  //c_handshake SlaveSync1;

  c_double_handshake SlaveSync2;
  c_double_handshake SlaveSync1;

//  MasterHardwareBus Master(A, D, ready, ack);
//  SlaveHardwareBus  Slave(A, D, ready, ack);
//

  HardwareBusProtocolTLM myHardwareBusProtocolTLM(A, D,TLMEvent1,TLMEvent2);

//
  MasterHardwareBusLinkAccess MasterLink(myHardwareBusProtocolTLM);
  SlaveHardwareBusLinkAccess SlaveLink(myHardwareBusProtocolTLM);

  void MasterRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len) {
    MasterLink.MasterRead(addr, data, len);
  }
  void MasterWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void *data, unsigned long len) {
    MasterLink.MasterWrite(addr, data, len);
  }
  
  void SlaveRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len) {
    SlaveLink.SlaveRead(addr, data, len);
  }
  void SlaveWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void *data, unsigned long len) {
    SlaveLink.SlaveWrite(addr, data, len);
  }

  void MasterSyncReceive() {
    char myChar = 'c';
    unsigned long mySize = 1; 
   SlaveSync1.receive(&myChar,mySize);
   //SlaveSync1.receive();
    printf("MASTER_sync_receive %llu\n",now()); 
  }
 
  void MasterSyncReceive2() {
    //SlaveSync2.receive();
  }

  void SlaveSyncSend() {
    char myChar = 'c';
    unsigned long mySize = 1; 
    SlaveSync1.send(&myChar, mySize);
    //SlaveSync1.send();
    printf("after slaveSYnc1 after send %llu \n",now()); 
  }

  
  void SlaveSyncSend2() {
    char myChar = 'c';
    unsigned long mySize = 1; 

    
   // SlaveSync2.send();
    SlaveSync2.send(&myChar, 1);
    //printf("after slaveSYnc2 send\n"); 
  }
//
  
//   void MasterSyncSend() {
//    char myChar;
//    //unsigned long mySize = 1; 
//    //MasterSync0.receive();
//    printf("999999999999999999\n"); 
//    //SlaveSync2.send();
//    SlaveSync2.send(&myChar, 1);
//    //MasterSync0.receive(&myChar, 1);
//    printf("in syncR\n"); 
//  }
//

  
//  void SlaveSyncReceive() {
//    char myChar = 'c';
//    unsigned long mySize = 1; 
//    //SlaveSync0.send(&myChar, 1);
//    
//     printf("in slaveSync2 \n"); 
//    SlaveSync2.receive(&myChar, 1);
//    //SlaveSync2.receive();
//    printf("after slaveSYnc2 send\n"); 
//  }
//
//

};
