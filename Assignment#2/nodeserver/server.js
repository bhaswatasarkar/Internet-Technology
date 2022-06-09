const io = require('socket.io')(8000, {
    cors: {
      origin: '*',
    }
  });

const users = {}
const broadcastmessagedatabase = new Array();

const multicastmessagedatabaseroom1 = new Array();


io.on('connection',socket=>{

    socket.on('new-user-joined',({username,roomname})=>{
        users[socket.id] = {username,roomname}
        socket.join(roomname)
        socket.broadcast.emit('user-joined',username);
        socket.broadcast.to(roomname).emit('user-joined-room',username)

        
        // for(let i=0;i<broadcastmessagedatabase.length;i++){
        //   if(broadcastmessagedatabase[i].user.roomname==roomname)
        //     multicastmessagedatabase.push({user:broadcastmessagedatabase[i].user,message:broadcastmessagedatabase[i].message})
        // }
        // console.log(multicastmessagedatabase)
        // io.to(socket.id).emit('old-multicast-messages-recover',multicastmessagedatabase);
        io.to(socket.id).emit('old-broadcast-messages-recover',broadcastmessagedatabase);
        if(roomname='Room1'){
          io.to(socket.id).emit('old-multicast-messages-recover',multicastmessagedatabaseroom1);
        }

    });

    socket.on('disconnect', ()=>{
      socket.broadcast.emit('left',users[socket.id].username)
    });

    socket.on('sendbroadcast',message=>{
 
        
        broadcastmessagedatabase.push({user:users[socket.id],message:message});
        
        socket.broadcast.emit('receive',{user: users[socket.id],message: message})
    });

    socket.on('sendmulticast',message=>{


      socket.broadcast.to(users[socket.id].roomname).emit('receivemulticast',{user: users[socket.id],message: message})

      if(users[socket.id].roomname=='Room1'){
        multicastmessagedatabaseroom1.push({user:users[socket.id],message:message})
        console.log(multicastmessagedatabaseroom1)
      }
    });

     



    function returnkey(nametosend){
       for(var i in users){
         if(users[i].username==nametosend){
           return i;
         }
       }
     }

    
    socket.on('sendunicast',({message,nametosend})=>{
        let socket_id_receiver = returnkey(nametosend)
        io.to(socket_id_receiver).emit('receiveunicast',{user: users[socket.id],message: message})
    })



    
    socket.on('sendimagebroadcast',(image)=>{
      socket.broadcast.emit('receiveimagebroadcast',image)
    })

    socket.on('sendimagemulticast',(image)=>{
      socket.broadcast.to(users[socket.id].roomname).emit('receiveimagemulticast',image)
    });

    
    socket.on('sendimageunicast',({image,nametosend})=>{
      let socket_id_receiver = returnkey(nametosend)
      io.to(socket_id_receiver).emit('receiveimageunicast',{user: users[socket.id],base64: image})
    });

});
