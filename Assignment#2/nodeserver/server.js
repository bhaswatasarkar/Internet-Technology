const io = require('socket.io')(8000, {
    cors: {
      origin: '*',
    }
  });

const users = {}

io.on('connection',socket=>{
    socket.on('new-user-joined',({username,roomname})=>{
        users[socket.id] = {username,roomname}
        socket.broadcast.emit('user-joined',username);
    });
    
    socket.on('send',message=>{
        socket.broadcast.emit('receive',{user: users[socket.id],message: message})
    });

});