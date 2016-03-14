module.exports = function(io){
	io.sockets.on('connection', function(client){
  		client.on('join', function(data){
    		client.broadcast.emit('message', { message: data + " just joined!"});
  		});
  //when client sends a message
  		client.on('message', function(data){
      		client.broadcast.emit('message', { message: data });      
  		});
  	});
}