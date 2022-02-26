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

});