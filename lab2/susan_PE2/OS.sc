interface OSAPI
{
void init();
//void start(int sched_alg);
//void interrupt_return();
//Task task_create(char *name, int type,sim_time period);
//void task_terminate();
//void task_sleep();
void task_activate(Task t);
//void task_endcycle();
//void task_kill(Task t);
Task par_start();
void par_end(Task t);
Task pre_wait();
void post_wait(Task t);
void time_wait(sim_time nsec);
};

channel OS implements OSAPI {
    Task current = 0;
    enum TASK {
        task1,
        task2,
        task3,
        task4,
        nullTask
    };

    event e1;
    event e2;
    event e3;
    event e4;
    TASK readQueue[NUM_OF_PROC];
    int head = tail = -1;
    QueueFull = 0;
    QueueEmpty = 1;
    
    //Queue
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

    Task pop() {
        Task taskID; 
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
    void trigger_event(event taskID) {
        switch(taskID):
        case(task1):
            notify(e1)    
            break;
        case(task2):
            notify(e2)    
            break;
        case(task3):
            notify(e3)    
            break;
        case(task4):
            notify(e4)    
            break;
    }

    void wait_event(event taskID) {
        switch(taskID):
        case(task1):
            wait(e1)    
            break;
        case(task2):
            wait(e2)    
            break;
        case(task3):
            wait(e3)    
            break;
        case(task4):
            wait(e4)    
            break;
    }

   TASK par_start() {
     return current;
   }
   
   par_end(Task t) {
      push(t); 
      dispatch();
   }
    
   void task_activate(Task t) {
        if(t != Task1) {
            push(t);
            wait_event(t);
        }
        current = t;
    }

    void schedule(TASK taskID) {
        return pop();
    }

    void dispatch(void) {
        current = schedule(taskID);
        if(current != nullTask)
            trigger_event(taskID);

    }

    void yield() {
        task = current;
        push(task);
        dispatch();
        wait_event(task);
    }
    void time_wait(time t) {
        waitfor(t);
        yield();
    }
    Task pre_wait(void) {
        Task t = current;
        dispatch(); return t;
    }
    void post_wait(Task t) {
        push(t);
        if (!current) dispatch();
        wait_even(t);
    }
    }
