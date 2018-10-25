export default {
    state: {
        user: '',
        roles: '',
        auth: false,
        data: '',
        manager: '',
        client: '',
        token: ''
    },
    mutations: {
        SET_USER(state, payload) {
            state.user = payload
        },
        SET_ROLES(state, payload) {
            state.roles = payload
        },
        SET_AUTH(state, payload) {
            state.auth = payload
        },
        SET_DATA(state, payload) {
            state.data = payload
        },
        SET_MANAGER(state, payload) {
            state.manager = payload
        },
        SET_CLIENT(state, payload) {
            state.client = payload
        },
        SET_TOKEN(state, payload) {
            state.token = payload
        }
    },
    getters: {
        getUser(state) {
            return state.user
        },
        getAuth(state) {
            return state.auth
        },
        getRoles(state) {
            return state.roles
        },
        getClient(state) {
            return state.client
        },
        getManager(state) {
            return state.manager
        }
    },
    actions: {
        /**
         * Авторизация
         * @param commit
         * @param state
         * @param payload
         */
        setAuth({commit, state}, payload) {
            commit('SET_DATA', payload)
            commit('SET_USER', payload.data.user)
            commit('SET_TOKEN', payload.data.accessToken)
            if (payload.data.primaryRole === 'ROLE_MANAGER') {
                commit('SET_MANAGER', payload.data.manager)
                commit('SET_ROLES', '/manager')
            } else if (payload.data.primaryRole === 'ROLE_CLIENT') {
                commit('SET_CLIENT', payload.data.client)
                commit('SET_ACCOUNTS', payload.data.accounts)
                commit('SET_ROLES', '/account')
            } else {
                commit('SET_ROLES', '/welcome')
            }
            commit('SET_AUTH', true)
        },
        /**
         * Выход из авторизированной сессии все авторизированные поля устанавливаются в NULL
         * @param commit
         */
        clearAuth({commit}) {
            commit('SET_AUTH', false)
            commit('SET_CLIENT', null)
            commit('SET_MANAGER', null)
            commit('SET_TOKEN', null)
            commit('SET_USER', null)
            commit('SET_ROLES', null)
            commit('SET_DATA', null)
        }
    }
}

