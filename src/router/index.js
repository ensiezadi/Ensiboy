import { createRouter, createWebHistory } from 'vue-router'
import backendLayout from '@/components/backendLayout.vue'

const backend = [
    {
        path: '/',
        redirect: '/backend/dashboard'
    },
    {
        path: '/backend',
        name: 'backend',
        // redirect: '/backend/dashboard',
        component: backendLayout,
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
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: backend
})

export default router