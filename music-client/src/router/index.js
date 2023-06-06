import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
const Other = resolve => {
    import('../pages/other/other').then((module) => {
        resolve(module)
    })
}

const Pending = resolve => {
    import('../pages/pending/pending').then((module) => {
        resolve(module)
    })
}

const Finished = resolve => {
    import('../pages/finished/finished').then((module) => {
        resolve(module)
    })
}

const Create = resolve => {
    import('../pages/create/create').then((module) => {
        resolve(module)
    })
}

export default new Router({
  routes: [
    {
        path: '/',
        redirect: '/other',
    },
    {
        path: '/other',
        component: Other,
    },
    {
        path: '/pending',
        component: Pending,
    },
    {
        path: '/finished',
        component: Finished,
    },
    {
        path: '/create',
        component: Create,
    },
  ]
})
