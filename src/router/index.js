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
                    title: '📊 数据分析',
                    icon: 'Notification'
                }
            },
            {
                path: 'history',
                component: () => import('@/views/history.vue'),
                meta: {
                    title: '😊 情绪日记',
                    icon: 'Aim'
                }
            },
            {
                path: 'knowledge',
                component: () => import('@/views/knowledge.vue'),
                meta: {
                    title: '💡 知识库',
                    icon: 'ChatSquare'
                }
            },
            {
                path: 'query',
                component: () => import('@/views/query.vue'),
                meta: {
                    title: '📞 咨询记录',
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

function getStoredUser() {
    try {
        const raw = localStorage.getItem('user')
        return raw ? JSON.parse(raw) : {}
    } catch (_error) {
        return {}
    }
}

function isAdminUser(user) {
    const userType = String(user?.userType ?? '')
    const role = String(user?.role ?? '').toLowerCase()
    return userType === '2' || role === 'admin' || role === 'administrator'
}

router.beforeEach((to, _from, next) => {
    const token = localStorage.getItem('token')
    const currentUser = getStoredUser()
    const isBackendRoute = to.path.startsWith('/backend')
    const isAuthRoute = to.path.startsWith('/auth')

    if (isBackendRoute && !token) {
        const redirect = encodeURIComponent(to.fullPath || '/backend/knowledge')
        next(`/auth/login?redirect=${redirect}`)
        return
    }

    if (isBackendRoute && token && !isAdminUser(currentUser)) {
        next('/frontend')
        return
    }

    if (isAuthRoute && token) {
        const redirect = typeof to.query.redirect === 'string' ? to.query.redirect : ''
        if (redirect && redirect.startsWith('/') && !redirect.startsWith('/auth')) {
            next(redirect)
            return
        }
        next(isAdminUser(currentUser) ? '/backend/dashboard' : '/frontend')
        return
    }

    next()
})

export default router