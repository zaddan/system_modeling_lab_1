#include "susan.sh"

import "detect_edges";
import "susan";
import "read_image";
import "write_image";
import "c_uchar7220_queue";


behavior Design(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_sender out_image_susan)
{

    c_uchar7220_queue in_image(1ul);
    c_uchar7220_queue out_image(1ul);
   
    c_int7220_queue r(1ul);
    c_uchar7220_queue mid(1ul);
    c_uchar7220_queue image_edge_draw(1ul);


    ReadImage read_image(start, image_buffer, in_image);
    Edges PE1(in_image, r, mid, image_edge_draw);
    Susan PE2(r, mid, image_edge_draw, out_image);
    WriteImage write_image(out_image, out_image_susan);

    void main(void) {
       par {
            read_image.main();
            PE1.main(); 
            PE2.main();
            write_image.main();
        }
    }
    
};
