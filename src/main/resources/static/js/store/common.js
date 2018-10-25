export default {
    state: {
        test: '',
        error: '',
        loading: false
    },
    mutations: {
        SET_TEST(state, payload) {
            state.test = payload
        },
        SET_ERROR(state, payload) {
            state.error = payload
        },
        IS_LOADING(state, payload) {
            state.loading = payload
        }
    },
    getters: {
        getTest(state) {
            return state.test
        },
        getError(state) {
            return state.error
        },
        getLoading(state) {
            return state.loading
        }
    },
    actions: {
        setTest({commit}, payload) {
            commit('SET_TEST', payload)
        },
        setError({commit}, payload) {
            commit('SET_ERROR', payload)
        },
        isLoading({commit}, payload) {
            commit('IS_LOADING', payload)
        }
    }
}
