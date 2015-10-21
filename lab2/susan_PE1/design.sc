#include "susan.sh"

import "detect_edges";
import "susan";
import "read_image";
import "write_image";
import "c_uchar7220_queue_both_SR";
//import "c_uchar7220_queue_R";
//import "c_uchar7220_queue_S";

behavior Design(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_sender out_image_susan)
{

    
    OS myOS;
    c_uchar7220_queue_R in_image(1ul, myOS);
    c_uchar7220_queue_S out_image(1ul, myOS);
   
    ReadImage read_image(start, image_buffer, in_image);
    Susan TASK_PE1(in_image, out_image, myOS);
    WriteImage write_image(out_image, out_image_susan);

    void main(void) {
       par {
            read_image.main();
            TASK_PE1.main();
//            PE1.main(); 
//            PE2.main();
            write_image.main();
        }
    }
    
};
