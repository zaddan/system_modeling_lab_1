#define BP_CONST 516 //migh have to modify later
#define X_SIZE_CONST 76
#define Y_SIZE_CONST 95
typedef  unsigned char uchar;
void susan_thin(int r[],uchar mid[],int x_size,int y_size);
void edge_draw(uchar *in, uchar *mid,int x_size,int y_size,int drawing_mode);
void setup_brightness_lut(uchar **bp,int thresh, int form);
void susan_edges(uchar *in,int *r,uchar *mid,uchar *bp,int max_no,int x_size,int y_size);
