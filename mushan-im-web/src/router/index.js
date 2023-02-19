import { createRouter,createWebHistory} from "vue-router";


// 路由信息
const routes = [

    {
        path: "/test",
        name: "test",
        component:()=>import("../views/test.vue")
    },

    {
      path: "/",
      name: "home",
      component:()=>import("../layout/index.vue"),
      redirect: 'list',
      children:[
          {
              path:'/list',
              name:"list",
              component:()=>import("../views/user/userList.vue"),
              children:[
                  {
                      path:'/chat',
                      name:"chat",
                      component:()=>import("../views/chat/index.vue"),
                  },
              ]
          },
          {
              path:'/add',
              name:"add",
              component:()=>import("../views/user/userAdd.vue"),
              children:[
                  {
                      path:'/userAdd',
                      name:"userAdd",
                      component:()=>import("../views/user/add.vue"),
                  },
              ]
          },
          {
              path:'/call',
              name:"call",
              component:()=>import("../views/conversation/index.vue"),

          }
      ]
    },



    {
        path: "/login",
        name: "login",
        component:  () => import('../views/login/index.vue'),
    }
];





// 导出路由
const router = createRouter({
    history: createWebHistory(),
    routes,

});


router.beforeEach((to, from, next) => {
    let token = localStorage.getItem("mushan-token");  // 获取token
    if(to.name != 'login'){  //如果不是去登录页面需要判断是否有token
        if (!token){
            next({name:"login"});
        }else {
            next();
        }

    }else {
        next();
    }

})


export default router;
