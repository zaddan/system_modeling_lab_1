#include "susan.sh"
import "OS";
//import "c_uchar7220_queue";
import "c_int7220_queue_both_SR";
import "c_uchar7220_queue_both_SR";
import "detect_edges";
import "susan_thin";
import "edge_draw";

behavior Susan(i_uchar7220_receiver in_image, i_uchar7220_sender out_image, OSAPI myOS) 
{
//behavior Susan(i_int7220_receiver r, i_uchar7220_receiver mid, i_uchar7220_receiver image_edge_draw, i_uchar7220_sender out_image, OSAPI myOS){
   
    c_int7220_queue_both_SR r(1ul, myOS);
    c_uchar7220_queue_both_SR mid_edge_draw(1ul, myOS);
    c_uchar7220_queue_both_SR mid(1ul, myOS);
    c_uchar7220_queue_both_SR image_edge_draw(1ul, myOS);
    Edges edges(in_image, r, mid, image_edge_draw, myOS);
    Thin thin(r, mid, mid_edge_draw, myOS);
    Draw draw(image_edge_draw, mid_edge_draw, out_image, myOS);
    void main(void)
    {
        TASK master;
        master = 3; 
        myOS.start(master);
        master = myOS.par_start();
        par{ 
            edges;
            thin;
            draw;
        }      
        myOS.par_end(master); 
    }
};   


