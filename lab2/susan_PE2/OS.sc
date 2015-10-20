#include <stdio.h>
typedef int TASK;
#define NUM_OF_PROC 4
interface OSAPI{
//    enum TASK {
//        task1,
//        task2,
//        task3,
//        task4,
//        nullTask
//    };
  
    TASK pre_wait(void);
    //void init();
    void task_activate(TASK t);
    //void start(int sched_alg);
    //void interrupt_return();
    //TASK task_create(char *name, int type,sim_time period);
    //void task_terminate();
    //void task_sleep();
    //void task_endcycle();
    //void task_kill(TASK t);
    TASK par_start(void);
    void par_end(TASK t);
    void post_wait(TASK t);
    void time_wait(int nsec);
    int schedule(void);
};

channel OS implements OSAPI {
  
    const TASK task1 = 0;
    const TASK task2 = 1;
    const TASK task3 = 2;
    const TASK task4 = 3;
    const TASK nullTask = -1; 
    

    TASK current = 0;
    event e1;
    event e2;
    event e3;
    event e4;
    TASK readyQueue[NUM_OF_PROC];
    int head =-1; 
    int tail = -1;
    int QueueFull = 0;
    int QueueEmpty = 1;
    //Queue
    void increment_tail(){
        tail++;
        if (tail == NUM_OF_PROC) 
            tail = 0;
    }


    void increment_head(){
        head++;
        if (head == NUM_OF_PROC) 
            head = 0;
    }
    bool push(TASK taskID) {
        if (tail == -1) {
            tail = 0;
            head = 0;
            readyQueue[0] = taskID; 
            increment_tail();   
        } 
        else if(QueueFull) {
            return false;
        }else{
            readyQueue[tail] = taskID;
            increment_tail();
            if (head == tail) {
                QueueFull = 1;
            }
        }
    }

    

    TASK pop() {
        TASK taskID; 
        if (QueueEmpty) {
            return nullTask;
        }
        else {
            taskID = readyQueue[head];
            increment_head(); 
            if (head == tail) {
                QueueEmpty = 1;
            }
            return taskID;   
        }
    }

    // OS functions
    void trigger_event(int taskID) {
        switch(taskID){
            case task1:
                notify(e1);
                break;
            case task2:
                notify(e2);
                break;
            case task3:
                notify(e3);   
                break;
            case task4:
                notify(e4);
                break;
        } 
    }

    void wait_event(int taskID) {
        switch(taskID){
            case task1:
                wait(e1)    ;
                break;
            case task2:
                wait(e2)    ;
                break;
            case task3:
                wait(e3)    ;
                break;
            case task4:
                wait(e4)   ; 
                break;
        } 
    }
   int schedule(void) {
        return pop();
    } 
   
   void dispatch(void) {
        current = schedule();
        if(current != nullTask)
            trigger_event(current);

    }

    TASK par_start(void) {
     return current;
   }
   
   void par_end(TASK t) {
      push(t); 
      dispatch();
   }
    
   void task_activate(TASK t) {
        if(t != task1) {
            push(t);
            wait_event(t);
        }
        current = t;
    }

    

    void yield() {
        TASK task;
        task = current;
        push(task);
        dispatch();
        wait_event(task);
    }
    void time_wait(int t) {
        waitfor(t);
        yield();
    }
    TASK pre_wait(void) {
        TASK t;
        t = current;
        dispatch(); return t;
    }
    void post_wait(TASK t) {
        push(t);
        if (!current) dispatch();
        wait_event(t);
    }
};
