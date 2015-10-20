#include "susan.sh"
import "OS.sc";

import "c_uchar7220_queue";
import "c_int7220_queue";

behavior SusanEdgesThread_PartA(uchar image_buffer[IMAGE_SIZE],  int r[IMAGE_SIZE], uchar bp[516],  in int thID, OSAPI myOS)
{
    
         
    void main(void) {

        int max_no;
        int i, j, n;
        uchar *p,*cp;
         
        myOS.task_activate(thID);
        max_no = MAX_NO_EDGES;
        //for (i=3;i<Y_SIZE-3;i++)
        for (i=3+(Y_SIZE-3-3)/PROCESSORS*thID; i<3+(Y_SIZE-3-3)/PROCESSORS*(thID+1) + (thID+1==PROCESSORS && (Y_SIZE-3-3)%PROCESSORS!=0 ? (Y_SIZE-3-3)%PROCESSORS : 0); i++) {
            for (j=3;j<X_SIZE-3;j++)
            {
                n=100;
                p=image_buffer + (i-3)*X_SIZE + j - 1;
                cp=bp + 258 + image_buffer[i*X_SIZE+j];

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=X_SIZE-3; 

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=X_SIZE-5;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=X_SIZE-6;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=2;
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=X_SIZE-6;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=X_SIZE-5;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=X_SIZE-3;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);

                if (n<=max_no)
                    r[i*X_SIZE+j] = max_no - n;
            
            waitfor(19000000);
            }

        }                               
        myOS.task_terminate();
    }           
    
};  

behavior SusanEdgesThread_PartB(uchar image_buffer[IMAGE_SIZE],  int r[IMAGE_SIZE], uchar mid[IMAGE_SIZE], uchar bp[516], in int thID, OSAPI myOS)
{
    
         
    void main(void) {

        int max_no;
        float z;
        int   do_symmetry, i, j, m, n, a, b, x, y, w;
        uchar c,*p,*cp;
        
        max_no = MAX_NO_EDGES;
        myOS.task_activate(thID);
             
            //for (i=4;i<Y_SIZE-4;i++)
            	for (i=4+(Y_SIZE-4-4)/PROCESSORS*thID; i<4+(Y_SIZE-4-4)/PROCESSORS*(thID+1) + (thID+1==PROCESSORS && (Y_SIZE-4-4)%PROCESSORS!=0 ? (Y_SIZE-4-4)%PROCESSORS : 0); i++){
                for (j=4;j<X_SIZE-4;j++)
                {
                    if (r[i*X_SIZE+j]>0)
                    {
                        m=r[i*X_SIZE+j];
                        n=max_no - m;
                        cp=bp + 258 + image_buffer[i*X_SIZE+j];

                        if (n>600)
                        {
                            p=image_buffer + (i-3)*X_SIZE + j - 1;
                            x=0;y=0;

                            c=*(cp-*p++);x-=c;y-=3*c;
                            c=*(cp-*p++);y-=3*c;
                            c=*(cp-*p);x+=c;y-=3*c;
                            p+=X_SIZE-3; 
                    
                            c=*(cp-*p++);x-=2*c;y-=2*c;
                            c=*(cp-*p++);x-=c;y-=2*c;
                            c=*(cp-*p++);y-=2*c;
                            c=*(cp-*p++);x+=c;y-=2*c;
                            c=*(cp-*p);x+=2*c;y-=2*c;
                            p+=X_SIZE-5;
                    
                            c=*(cp-*p++);x-=3*c;y-=c;
                            c=*(cp-*p++);x-=2*c;y-=c;
                            c=*(cp-*p++);x-=c;y-=c;
                            c=*(cp-*p++);y-=c;
                            c=*(cp-*p++);x+=c;y-=c;
                            c=*(cp-*p++);x+=2*c;y-=c;
                            c=*(cp-*p);x+=3*c;y-=c;
                            p+=X_SIZE-6;

                            c=*(cp-*p++);x-=3*c;
                            c=*(cp-*p++);x-=2*c;
                            c=*(cp-*p);x-=c;
                            p+=2;
                            c=*(cp-*p++);x+=c;
                            c=*(cp-*p++);x+=2*c;
                            c=*(cp-*p);x+=3*c;
                            p+=X_SIZE-6;
                    
                            c=*(cp-*p++);x-=3*c;y+=c;
                            c=*(cp-*p++);x-=2*c;y+=c;
                            c=*(cp-*p++);x-=c;y+=c;
                            c=*(cp-*p++);y+=c;
                            c=*(cp-*p++);x+=c;y+=c;
                            c=*(cp-*p++);x+=2*c;y+=c;
                            c=*(cp-*p);x+=3*c;y+=c;
                            p+=X_SIZE-5;

                            c=*(cp-*p++);x-=2*c;y+=2*c;
                            c=*(cp-*p++);x-=c;y+=2*c;
                            c=*(cp-*p++);y+=2*c;
                            c=*(cp-*p++);x+=c;y+=2*c;
                            c=*(cp-*p);x+=2*c;y+=2*c;
                            p+=X_SIZE-3;

                            c=*(cp-*p++);x-=c;y+=3*c;
                            c=*(cp-*p++);y+=3*c;
                            c=*(cp-*p);x+=c;y+=3*c;

                            z = sqrt((float)((x*x) + (y*y)));
                            if (z > (0.9*(float)n)) /* 0.5 */
	                        {
                                do_symmetry=0;
                                if (x==0)
                                    z=1000000.0;
                                else
                                    z=((float)y) / ((float)x);
                                if (z < 0) { z=-z; w=-1; }
                                else w=1;
                                if (z < 0.5) { /* vert_edge */ a=0; b=1; }
                                else { if (z > 2.0) { /* hor_edge */ a=1; b=0; }
                                else { /* diag_edge */ if (w>0) { a=1; b=1; }
                                                       else { a=-1; b=1; }}}
                                if ( (m > r[(i+a)*X_SIZE+j+b]) && (m >= r[(i-a)*X_SIZE+j-b]) &&
                                     (m > r[(i+(2*a))*X_SIZE+j+(2*b)]) && (m >= r[(i-(2*a))*X_SIZE+j-(2*b)]) )
                                  mid[i*X_SIZE+j] = 1;
                            }
                            else
                                do_symmetry=1;
                        }
                        else 
                            do_symmetry=1;

                        if (do_symmetry==1)
	                    { 
                            p=image_buffer + (i-3)*X_SIZE + j - 1;
                            x=0; y=0; w=0;

                              /*   |      \
                                   y  -x-  w
                                   |        \   */

                            c=*(cp-*p++);x+=c;y+=9*c;w+=3*c;
                            c=*(cp-*p++);y+=9*c;
                            c=*(cp-*p);x+=c;y+=9*c;w-=3*c;
                            p+=X_SIZE-3; 
                      
                            c=*(cp-*p++);x+=4*c;y+=4*c;w+=4*c;
                            c=*(cp-*p++);x+=c;y+=4*c;w+=2*c;
                            c=*(cp-*p++);y+=4*c;
                            c=*(cp-*p++);x+=c;y+=4*c;w-=2*c;
                            c=*(cp-*p);x+=4*c;y+=4*c;w-=4*c;
                            p+=X_SIZE-5;
                        
                            c=*(cp-*p++);x+=9*c;y+=c;w+=3*c;
                            c=*(cp-*p++);x+=4*c;y+=c;w+=2*c;
                            c=*(cp-*p++);x+=c;y+=c;w+=c;
                            c=*(cp-*p++);y+=c;
                            c=*(cp-*p++);x+=c;y+=c;w-=c;
                            c=*(cp-*p++);x+=4*c;y+=c;w-=2*c;
                            c=*(cp-*p);x+=9*c;y+=c;w-=3*c;
                            p+=X_SIZE-6;

                            c=*(cp-*p++);x+=9*c;
                            c=*(cp-*p++);x+=4*c;
                            c=*(cp-*p);x+=c;
                            p+=2;
                            c=*(cp-*p++);x+=c;
                            c=*(cp-*p++);x+=4*c;
                            c=*(cp-*p);x+=9*c;
                            p+=X_SIZE-6;
                        
                            c=*(cp-*p++);x+=9*c;y+=c;w-=3*c;
                            c=*(cp-*p++);x+=4*c;y+=c;w-=2*c;
                            c=*(cp-*p++);x+=c;y+=c;w-=c;
                            c=*(cp-*p++);y+=c;
                            c=*(cp-*p++);x+=c;y+=c;w+=c;
                            c=*(cp-*p++);x+=4*c;y+=c;w+=2*c;
                            c=*(cp-*p);x+=9*c;y+=c;w+=3*c;
                            p+=X_SIZE-5;
                     
                            c=*(cp-*p++);x+=4*c;y+=4*c;w-=4*c;
                            c=*(cp-*p++);x+=c;y+=4*c;w-=2*c;
                            c=*(cp-*p++);y+=4*c;
                            c=*(cp-*p++);x+=c;y+=4*c;w+=2*c;
                            c=*(cp-*p);x+=4*c;y+=4*c;w+=4*c;
                            p+=X_SIZE-3;

                            c=*(cp-*p++);x+=c;y+=9*c;w-=3*c;
                            c=*(cp-*p++);y+=9*c;
                            c=*(cp-*p);x+=c;y+=9*c;w+=3*c;

                            if (y==0)
                                z = 1000000.0;
                            else
                                z = ((float)x) / ((float)y);
                            if (z < 0.5) { /* vertical */ a=0; b=1; }
                            else { if (z > 2.0) { /* horizontal */ a=1; b=0; }
                                       else { /* diagonal */ if (w>0) { a=-1; b=1; }
                                                    else { a=1; b=1; }}}
                            if ( (m > r[(i+a)*X_SIZE+j+b]) && (m >= r[(i-a)*X_SIZE+j-b]) &&
                                   (m > r[(i+(2*a))*X_SIZE+j+(2*b)]) && (m >= r[(i-(2*a))*X_SIZE+j-(2*b)]) )
                                mid[i*X_SIZE+j] = 2;	
                        }
                    }
                
            myOS.time_wait(20000000); 
                }                            
         } 
        myOS.task_terminate();
    }           
    
};  

behavior  SusanEdges_ReadInput(i_uchar7220_receiver in_image, uchar in_image_buffer[IMAGE_SIZE], int r[IMAGE_SIZE], uchar mid[IMAGE_SIZE]) 
{
    void main(void) {
        in_image.receive(&in_image_buffer);
        
        memset (mid,100,X_SIZE * Y_SIZE); /* note not set to zero */
        memset (r,0,X_SIZE * Y_SIZE * sizeof(int));
    }
};

behavior SusanEdges_WriteOutput(i_int7220_sender out_r, i_uchar7220_sender out_mid, 
                                                            i_uchar7220_sender out_image, int r[IMAGE_SIZE], 
                                                            uchar mid[IMAGE_SIZE], uchar out_image_buffer[IMAGE_SIZE])
{

    void main(void) {
        out_r.send(r);
        out_mid.send(mid);
        out_image.send(out_image_buffer);
    }
};

behavior SusanEdges_PartA (uchar image_buffer[IMAGE_SIZE],  int r[IMAGE_SIZE], uchar bp[516], OSAPI myOS)
{
    SusanEdgesThread_PartA susan_edges_a_thread_0(image_buffer, r, bp, 0, myOS);
    SusanEdgesThread_PartA susan_edges_a_thread_1(image_buffer, r, bp, 1, myOS);
    
    void main(void) {
        TASK master;
        master = 3; 
        myOS.start(master);
        master = myOS.par_start();
        par { 
            susan_edges_a_thread_0;
            susan_edges_a_thread_1;
        } 
        myOS.par_end(master); 
    }
};

behavior SusanEdges_PartB(uchar image_buffer[IMAGE_SIZE],  int r[IMAGE_SIZE], uchar mid[IMAGE_SIZE], uchar bp[516], OSAPI myOS)
{
    SusanEdgesThread_PartB susan_edges_b_thread_0(image_buffer, r, mid, bp, 0, myOS);
    SusanEdgesThread_PartB susan_edges_b_thread_1(image_buffer, r, mid, bp, 1, myOS);
    
    void main(void) {                 
        TASK master;
        master = 3; 
        myOS.start(master);
        par { 
            susan_edges_b_thread_0;
            susan_edges_b_thread_1;
        } 
        myOS.par_end(master); 
    }
};

behavior SusanEdges(i_uchar7220_receiver in_image, i_int7220_sender out_r, i_uchar7220_sender out_mid, uchar bp[516], i_uchar7220_sender out_image, OSAPI myOS)
{
  
    uchar image_buffer[IMAGE_SIZE];
    int r[IMAGE_SIZE];
    uchar mid[IMAGE_SIZE];

    SusanEdges_ReadInput susan_edges_read_input(in_image, image_buffer, r, mid); 
    SusanEdges_WriteOutput susan_edges_write_output(out_r, out_mid, out_image,  r, mid, image_buffer);
    SusanEdges_PartA susan_edges_a(image_buffer, r, bp, myOS);
    SusanEdges_PartB susan_edges_b(image_buffer, r, mid, bp, myOS);

                 
    void main(void) {
        
        fsm {
                    susan_edges_read_input: goto susan_edges_a;
                    susan_edges_a: goto susan_edges_b;
                    susan_edges_b: goto susan_edges_write_output;
                    susan_edges_write_output: {}
        }                      
    }           
    
};    


