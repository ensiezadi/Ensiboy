import { createRouter, createWebHistory } from 'vue-router'
import BackendLayout from '@/components/BackendLayout.vue'
import AuthLayout from '@/components/AuthLayout.vue'

const backend = [
    {
        path: '/',
        redirect: '/frontend'
    },
    {
        path: '/frontend',
        component: () => import('@/views/front.vue'),
        meta: {
            title: 'home',
            icon: 'House'
        }
    },
    {
        path: '/login',
        redirect: '/auth/login'
    },
    {
        path: '/register',
        redirect: '/auth/register'
    },
    {
        path: '/backend',
        name: 'backend',
        // redirect: '/backend/dashboard',
        component: BackendLayout,
        children: [
            {
                path: '',
                component: { template: '<div> xuan ze gong neng</div>' }
            },
            {
                path: 'dashboard',
                component: () => import('@/views/dashboard.vue'),
                meta: {
                    title: 'dashboard',
                    icon: 'Notification'
                }
            },
            {
                path: 'history',
                component: () => import('@/views/history.vue'),
                meta: {
                    title: 'history views',
                    icon: 'Aim'
                }
            },
            {
                path: 'knowledge',
                component: () => import('@/views/knowledge.vue'),
                meta: {
                    title: 'knowledge',
                    icon: 'ChatSquare'
                }
            },
            {
                path: 'query',
                component: () => import('@/views/query.vue'),
                meta: {
                    title: 'help records',
                    icon: 'Message'
                }
            }
        ]
    },
    {
        path: '/auth',
        redirect: '/auth/login',
        component: AuthLayout,
        children: [
            {
                path: 'login',
                component: () => import('@/views/login.vue'),
                meta: {
                    title: 'login',
                    icon: 'Message'
                }
            },
            {
                path: 'register',
                component: () => import('@/views/register.vue'),
                meta: {
                    title: 'register',
                    icon: 'Message'
                }
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: backend
})

export default router