// c_BP_queue.sc:	instantiation of template c_typed_queue.sh
//			with type 'image'

#include <c_typed_queue.sh>	/* make the template available */
#include "constant.hh"

// define the tranceiver interface for data type 'bpArray'
DEFINE_I_TYPED_TRANCEIVER(BP, bpArray)

// define the sender interface for data type 'bpArray'
DEFINE_I_TYPED_SENDER(BP, bpArray)

// define the receiver interface for data type 'bpArray'
DEFINE_I_TYPED_RECEIVER(BP, bpArray)


// define the queue channel for data type 'bpArray'
DEFINE_C_TYPED_QUEUE(BP, bpArray)


// EOF c_image_queue.sc
