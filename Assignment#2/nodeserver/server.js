const io = require('socket.io')(8000, {
    cors: {
      origin: '*',
    }
  });

const users = {}

io.on('connection',socket=>{
    socket.on('new-user-joined',nameuser=>{
        users[socket.id] = nameuser
        socket.broadcast.emit('user-joined',nameuser);
    });
    
    socket.on('send',message=>{
        socket.broadcast.emit('receive',{nameuser: users[socket.id],message: message})
    });

});