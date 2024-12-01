import GamesView from '@/views/GamesView.vue'
import StaffView from '@/views/StaffView.vue'
import AuthView from '@/views/AuthView.vue'
import CustomerGamesView from '@/views/CustomerGamesView.vue'
import CartView from '@/views/CartView.vue'
import WishlistView from '@/views/WishlistView.vue'
import OrderHistoryView from '@/views/OrderHistoryView.vue'
import StaffGamesView from '@/views/StaffGamesView.vue'
import CustomersView from '@/views/CustomersView.vue'
import CategoriesView from '@/views/CategoriesView.vue'
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'auth',
      component: AuthView,
    },
    {
      path: '/games',
      name: 'game',
      component: GamesView
    },
    {
      path: '/staff',
      name: 'staff',
      component: StaffView
    },
    {
      path: '/customers',
      name: 'customers',
      component: CustomersView
    },
    {
      path: '/categories',
      name: 'categories',
      component: CategoriesView
    },
    {
      path: '/customergames',
      name: 'customergames',
      component: CustomerGamesView
    },
    {
      path: '/cart',
      name: 'cart',
      component: CartView
    },
    {
      path: '/wishlist',
      name: 'wishlist',
      component: WishlistView
    },
    {
      path: '/orderhistory',
      name: 'orderhistory',
      component: OrderHistoryView
    },
    {
      path: '/staffgames',
      name: 'staffgames',
      component: StaffGamesView
    },
    // etc.
  ],
})

export default router