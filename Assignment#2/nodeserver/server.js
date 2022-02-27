const io = require('socket.io')(8000, {
    cors: {
      origin: '*',
    }
  });

const users = {}

io.on('connection',socket=>{

    socket.on('new-user-joined',({username,roomname})=>{
        users[socket.id] = {username,roomname}
        socket.join(roomname)
        socket.broadcast.emit('user-joined',username);
    });
    
    socket.on('sendbroadcast',message=>{
        socket.broadcast.emit('receive',{user: users[socket.id],message: message})
    });

    socket.on('sendmulticast',message=>{
      socket.broadcast.to(users[socket.id].roomname).emit('receivemulticast',{user: users[socket.id],message: message})
    });

     
    function returnkey(nametosend){
       for(var i in users){
         if(users[i].username==nametosend){
           return i;
         }
       }
     }
    
    socket.on('sendunicast',({message,nametosend})=>{
      console.log(message);

        let socket_id_receiver = returnkey(nametosend)
      console.log(users[socket_id_receiver])
        io.to(socket_id_receiver).emit('receiveunicast',{user: users[socket.id],message: message})
    })

});