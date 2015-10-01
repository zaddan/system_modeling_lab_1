#include <stdio.h>
#include <stdlib.h>
#include "sim.sh"
#include <string.h>
#include <math.h>
#include "constant.hh"

import "wait_behav";
import "c_queue"; 
import "c_double_handshake";





behavior susan_edges(i_receiver in_port1, i_receiver in_port2, i_sender out_portR, i_sender out_portMid)	 
{

    void main(void)
    {	
        
        int max_no;
        float z;	
        int x_size, y_size; 
        // assign x_size and y_size values if required
        unsigned char inData[X_SIZE_CONST*Y_SIZE_CONST];
        unsigned char bp[BP_CONST];
        int r[X_SIZE_CONST*Y_SIZE_CONST];
        int   do_symmetry, i, j, m, n, a, b, x, y, w;

        int oy;  
        unsigned char mid[X_SIZE_CONST*Y_SIZE_CONST]; //TODO find mid's size
        int l; 
        unsigned char c,*p,*cp;
        //        for (l =0; l < IMAGE_SIZE; l++){
        //          mid[l] = 100; 
        //        }
        max_no = MAX_NO_EDGES;
        x_size = X_SIZE_CONST;
        y_size = Y_SIZE_CONST;
        in_port1.receive(inData,(X_SIZE_CONST*Y_SIZE_CONST)); // receive the "in"
        in_port2.receive(bp, BP_CONST); // receive the "bp"

        //in_port.receive(r,(X_SIZE_CONST*Y_SIZE_CONST));  //receive "r"
        //TODO memset mid to 100

        memset (mid,100,x_size * y_size * sizeof(int));
        memset (r,0,x_size * y_size * sizeof(int));
        for (i=3;i<y_size-3;i++)
            for (j=3;j<x_size-3;j++)
            {
                n=100;
                p=inData + (i-3)*x_size + j - 1;
                cp=bp + inData[i*x_size+j] + 258;
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-3; 

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-5;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-6;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=2;
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-6;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-5;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);
                p+=x_size-3;

                n+=*(cp-*p++);
                n+=*(cp-*p++);
                n+=*(cp-*p);

                if (n<=max_no)
                    r[i*x_size+j] = max_no - n;
            }

        for (i=4;i<y_size-4;i++)
            for (j=4;j<x_size-4;j++)
            {
                if (r[i*x_size+j]>0)
                {
                    m=r[i*x_size+j];
                    n=max_no - m;
                    cp=bp + inData[i*x_size+j] + 258;

                    if (n>600)
                    {
                        p=inData + (i-3)*x_size + j - 1;
                        x=0;y=0;

                        c=*(cp-*p++);x-=c;y-=3*c;
                        c=*(cp-*p++);y-=3*c;
                        c=*(cp-*p);x+=c;y-=3*c;
                        p+=x_size-3; 

                        c=*(cp-*p++);x-=2*c;y-=2*c;
                        c=*(cp-*p++);x-=c;y-=2*c;
                        c=*(cp-*p++);y-=2*c;
                        c=*(cp-*p++);x+=c;y-=2*c;
                        c=*(cp-*p);x+=2*c;y-=2*c;
                        p+=x_size-5;

                        c=*(cp-*p++);x-=3*c;y-=c;
                        c=*(cp-*p++);x-=2*c;y-=c;
                        c=*(cp-*p++);x-=c;y-=c;
                        c=*(cp-*p++);y-=c;
                        c=*(cp-*p++);x+=c;y-=c;
                        c=*(cp-*p++);x+=2*c;y-=c;
                        c=*(cp-*p);x+=3*c;y-=c;
                        p+=x_size-6;

                        c=*(cp-*p++);x-=3*c;
                        c=*(cp-*p++);x-=2*c;
                        c=*(cp-*p);x-=c;
                        p+=2;
                        c=*(cp-*p++);x+=c;
                        c=*(cp-*p++);x+=2*c;
                        c=*(cp-*p);x+=3*c;
                        p+=x_size-6;

                        c=*(cp-*p++);x-=3*c;y+=c;
                        c=*(cp-*p++);x-=2*c;y+=c;
                        c=*(cp-*p++);x-=c;y+=c;
                        c=*(cp-*p++);y+=c;
                        c=*(cp-*p++);x+=c;y+=c;
                        c=*(cp-*p++);x+=2*c;y+=c;
                        c=*(cp-*p);x+=3*c;y+=c;
                        p+=x_size-5;

                        c=*(cp-*p++);x-=2*c;y+=2*c;
                        c=*(cp-*p++);x-=c;y+=2*c;
                        c=*(cp-*p++);y+=2*c;
                        c=*(cp-*p++);x+=c;y+=2*c;
                        c=*(cp-*p);x+=2*c;y+=2*c;
                        p+=x_size-3;

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
                                    if ( (m > r[(i+a)*x_size+j+b]) && (m >= r[(i-a)*x_size+j-b]) &&
                                            (m > r[(i+(2*a))*x_size+j+(2*b)]) && (m >= r[(i-(2*a))*x_size+j-(2*b)]) )
                                        mid[i*x_size+j] = 1;
                        }
                        else
                            do_symmetry=1;
                    }
                    else 
                        do_symmetry=1;

                    if (do_symmetry==1)
                    { 
                        p=inData + (i-3)*x_size + j - 1;
                        x=0; y=0; w=0;

                        /*   |      \
                             y  -x-  w
                             |        \   */

                        c=*(cp-*p++);x+=c;y+=9*c;w+=3*c;
                        c=*(cp-*p++);y+=9*c;
                        c=*(cp-*p);x+=c;y+=9*c;w-=3*c;
                        p+=x_size-3; 

                        c=*(cp-*p++);x+=4*c;y+=4*c;w+=4*c;
                        c=*(cp-*p++);x+=c;y+=4*c;w+=2*c;
                        c=*(cp-*p++);y+=4*c;
                        c=*(cp-*p++);x+=c;y+=4*c;w-=2*c;
                        c=*(cp-*p);x+=4*c;y+=4*c;w-=4*c;
                        p+=x_size-5;

                        c=*(cp-*p++);x+=9*c;y+=c;w+=3*c;
                        c=*(cp-*p++);x+=4*c;y+=c;w+=2*c;
                        c=*(cp-*p++);x+=c;y+=c;w+=c;
                        c=*(cp-*p++);y+=c;
                        c=*(cp-*p++);x+=c;y+=c;w-=c;
                        c=*(cp-*p++);x+=4*c;y+=c;w-=2*c;
                        c=*(cp-*p);x+=9*c;y+=c;w-=3*c;
                        p+=x_size-6;

                        c=*(cp-*p++);x+=9*c;
                        c=*(cp-*p++);x+=4*c;
                        c=*(cp-*p);x+=c;
                        p+=2;
                        c=*(cp-*p++);x+=c;
                        c=*(cp-*p++);x+=4*c;
                        c=*(cp-*p);x+=9*c;
                        p+=x_size-6;

                        c=*(cp-*p++);x+=9*c;y+=c;w-=3*c;
                        c=*(cp-*p++);x+=4*c;y+=c;w-=2*c;
                        c=*(cp-*p++);x+=c;y+=c;w-=c;
                        c=*(cp-*p++);y+=c;
                        c=*(cp-*p++);x+=c;y+=c;w+=c;
                        c=*(cp-*p++);x+=4*c;y+=c;w+=2*c;
                        c=*(cp-*p);x+=9*c;y+=c;w+=3*c;
                        p+=x_size-5;

                        c=*(cp-*p++);x+=4*c;y+=4*c;w-=4*c;
                        c=*(cp-*p++);x+=c;y+=4*c;w-=2*c;
                        c=*(cp-*p++);y+=4*c;
                        c=*(cp-*p++);x+=c;y+=4*c;w+=2*c;
                        c=*(cp-*p);x+=4*c;y+=4*c;w+=4*c;
                        p+=x_size-3;

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
                                if ( (m > r[(i+a)*x_size+j+b]) && (m >= r[(i-a)*x_size+j-b]) &&
                                        (m > r[(i+(2*a))*x_size+j+(2*b)]) && (m >= r[(i-(2*a))*x_size+j-(2*b)]) )
                                    mid[i*x_size+j] = 2;	
                    }
                }



            }
        //        for (x = 0; x < IMAGE_SIZE; x++) {
        //            printf("%d\n", r[x]);
        //        }
        //        
        out_portR.send((void*)r,4*IMAGE_SIZE); //TODO figure out the size
        out_portMid.send(mid,IMAGE_SIZE); //TODO figure out the size	
    
    }      
};


behavior susan_edges_fsm(i_receiver in_port1, i_receiver in_port2, i_sender out_portR, i_sender out_portMid)	 
{
    susan_edges sedge(in_port1, in_port2, out_portR, out_portMid);//find how are they connected
    void main(void) {
        fsm {   sedge: {  
            goto sedge;
        }
        } 
    }
};
