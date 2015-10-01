// c_int_queue.sc:	instantiation of template c_typed_queue.sh
//			with type 'int'

#include <c_typed_queue.sh>	/* make the template available */
#include "constant.hh"

// define the tranceiver interface for data type 'int'
DEFINE_I_TYPED_TRANCEIVER(intR, intR)

// define the sender interface for data type 'int'
DEFINE_I_TYPED_SENDER(intR, intR)

// define the receiver interface for data type 'int'
DEFINE_I_TYPED_RECEIVER(intR, intR)


// define the queue channel for data type 'int'
DEFINE_C_TYPED_QUEUE(intR, intR)


// EOF c_image_queue.sc
