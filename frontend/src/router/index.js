import { createRouter, createWebHistory } from "vue-router";
import UserTemplate from "@/view/user/UserTemplate.vue";
import LoginPage from "@/view/user/pages/LoginPage.vue";
import SignupPage from "@/view/user/pages/SignupPage.vue";
import PasswordPage from "@/view/user/pages/PasswordPage.vue";
import CompletePage from "@/view/user/pages/CompletePage.vue";
import SocialLoginSuccessPage from "@/view/user/pages/SocialLoginSuccessPage.vue";
// import { useWorkspaceStore } from "@/stores/workspace/useWorkspaceStore";
import {axiosInstance} from "@/utils/axiosInstance";

const routes = [
    {
        path: '/',
        name: 'Thumbnail',
        component: () => import('@/view/thumbnail/ThumbnailPage.vue')
    },

    {
        path: '/social/login/success',
        name: 'SocialLoginSuccess',
        component: SocialLoginSuccessPage,
    },

    // user
    {
        path: '/user',
        name: 'UserTemplate',
        component: UserTemplate,
        children: [
            {
                path: 'login',
                name: 'Login',
                component: LoginPage,
                beforeEnter: (to, from, next) => {
                    const token = sessionStorage.getItem('userInfo');
                    if (token) {
                        next('/my/dashboard');
                    } else {
                        next();
                    }
                }
            },
            {
                path: 'signup',
                name: 'Signup',
                component: SignupPage
            },
            {
                path: 'password',
                name: 'Password',
                component: PasswordPage
            },
            {
                path: 'password-reset',
                name: 'PasswordReset',
                component: () => import('@/view/user/pages/PasswordResetPage.vue')
            },
            {
                path: 'complete',
                name: 'Complete',
                component: CompletePage
            }
        ]
    },

    // my
    {
        path: '/my',
        name: 'My',
        component: () => import('@/layouts/ContentsArea.vue'),
        meta: { requiresAuth: true },
        children: [
            {
                path: 'create',
                name: 'WorkSpaceCreate',
                component: () => import('@/view/scrum/create/WorkSpaceCreatePage.vue')
            },
            {
                path: 'dashboard',
                name: 'MyDashboard',
                component: () => import('@/view/dashboard/MyDashBoardPage.vue')
            },
            {
                path: 'schedule',
                name: 'MySchedule',
                children: [
                    {
                        path: 'monthly',
                        name: 'MyMonthly',
                        component: () => import('@/view/schedule/monthly/MyMonthlyPage.vue')
                    },
                    {
                        path: 'weekly',
                        name: 'myWeekly',
                        component: () => import('@/view/schedule/weekly/MyWeeklyPage.vue')
                    }
                ]
            },
            {
                path: 'task',
                name: 'MyTask',
                component: () => import('@/view/scrum/Task/TaskTemplate.vue'),
                children: [
                    {
                        path: 'kanban',
                        name: 'MyTaskKanban',
                        component: () => import('@/view/scrum/Task/kanban/MyTaskKanbanPage.vue')
                    },
                    {
                        path: 'list',
                        name: 'MyTaskList',
                        component: () => import('@/view/scrum/Task/list/MyTaskListPage.vue')
                    },
                ]
            }
        ]
    },

    // dashboard
    {
        path: '/workspace/:workspaceId',
        name: 'Workspace',
        component: () => import('@/layouts/ContentsArea.vue'),
        meta: { requiresAuth: true },
        children: [
            // dashboard
            {
                path: 'dashboard',
                name: 'WorkspaceDashboard',
                component: () => import('@/view/dashboard/WorkspaceDashBoardPage.vue')
            },
            // schedule
            {
                path: 'schedule',
                name: 'WorkspaceSchedule',
                children: [
                    {
                        path: 'monthly',
                        name: 'WorkspaceMonthly',
                        component: () => import('@/view/schedule/monthly/WorkSpaceMonthly.vue')
                    },
                    {
                        path: 'weekly',
                        name: 'WorkspaceWeekly',
                        component: () => import('@/view/schedule/weekly/WorkSpaceWeeklyPage.vue')
                    }
                ]
            },

            // scrum
            {
                path: 'scrum',
                name: 'WorkspaceScrum',
                children: [
                    {
                        path: 'label',
                        name: 'WorkspaceScrumLabel',
                        component: () => import('@/view/scrum/list/LabelListPage.vue')
                    },
                    {
                        path: 'sprint',
                        name: 'WorkspaceScrumSprint',
                        children: [
                            {
                                path: 'list',
                                name: 'WorkspaceScrumSprintList',
                                component: () => import('@/view/scrum/list/SprintListPage.vue')
                            },
                            {
                                path: 'create',
                                name: 'WorkspaceSprintCreate',
                                component: () => import('@/view/scrum/create/SprintCreatePage.vue')
                            },
                            {
                                path: 'detail/:id',
                                name: 'WorkspaceSprintDetail',
                                component: () => import('@/view/scrum/detail/SprintDetailPage.vue')
                            }
                        ]
                    },
                    {
                        path: 'task',
                        name: 'WorkspaceScrumTask',
                        component: () => import('@/view/scrum/Task/TaskTemplate.vue'),
                        children: [
                            {
                                path: 'create',
                                name: 'WorkspaceTaskCreate',
                                component: () => import('@/view/scrum/Task/create/WorkSpaceTaskCreatePage.vue')
                            },
                            {
                                path: 'kanban',
                                name: 'WorkspaceTaskKanban',
                                component: () => import('@/view/scrum/Task/kanban/WorkSpaceTaskKanbanPage.vue')
                            },
                            {
                                path: 'list',
                                name: 'WorkspaceTaskList',
                                component: () => import('@/view/scrum/Task/list/WorkSpaceTaskListPage.vue')
                            },
                            {
                                path: 'timeline',
                                name: 'WorkspaceTaskTimeline',
                                component: () => import('@/view/scrum/Task/timeline/TimeLinePage.vue')
                            },
                        ]
                    },
                    {
                        path: 'issue',
                        name: 'WorkspaceScrumIssue',
                        children: [
                            {
                                path: 'list',
                                name: 'WorkspaceIssueList',
                                component: () => import('@/view/scrum/list/IssueListPage.vue')
                            },
                            {
                                path: 'detail/:id',
                                name: 'WorkspaceIssueDetail',
                                component: () => import('@/view/scrum/detail/IssueDetailPage.vue')
                            },
                            {
                                path: 'create',
                                name: 'WorkspaceIssueCreate',
                                component: () => import('@/view/scrum/create/IssueCreatePage.vue')
                            }
                            ]
                    },
                    {
                        path: 'meeting',
                        name: 'BoardMeeting',
                        children: [
                            {
                                path: 'list',
                                name: 'BoardMeetingList',
                                component: () => import('@/view/scrum/meeting/MeetingListPage.vue')
                            },
                            {
                                path: 'detail/:meetingId',
                                name: 'BoardMeetingDetail',
                                component: () => import('@/view/scrum/meeting/MeetingDetailPage.vue')
                            },
                            {
                                path: 'create',
                                name: 'BoardMeetingCreate',
                                component: () => import('@/view/scrum/meeting/MeetingCreatePage.vue')
                            },
                            {
                                path: 'edit/:meetingId',
                                name: 'BoardMeetingEdit',
                                component: () => import('@/view/scrum/meeting/MeetingEditPage.vue')
                            }
                        ]
                    },
                    {
                        path: 'board',
                        name: 'Board',
                        children: [
                            {
                                path: 'error',
                                name: 'Error',
                                children: [
                                    {
                                        path: 'list',
                                        name: 'ErrorList',
                                        component: () => import('@/view/board/list/ErrorListPage.vue')
                                    },
                                    {
                                        path: 'detail/:boardId',
                                        name: 'ErrorDetail',
                                        component: () => import('@/view/board/detail/ErrorDetailPage.vue')
                                    },
                                    {
                                        path: 'create',
                                        name: 'ErrorCreate',
                                        component: () => import('@/view/board/create/ErrorCreatePage.vue')
                                    }
                                ]
                            },
                            {
                                path: 'qa',
                                name: 'QA',
                                children: [
                                    {
                                        path: 'list',
                                        name: 'QAList',
                                        component: () => import('@/view/board/list/QAListPage.vue')
                                    },
                                    {
                                        path: 'detail/:id',
                                        name: 'QADetail',
                                        component: () => import('@/view/board/detail/QADetailPage.vue')
                                    },
                                    {
                                        path: 'create',
                                        name: 'QACreate',
                                        component: () => import('@/view/board/create/QACreatePage.vue')
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            {
                path: 'chat',
                name: 'Chat',
                component: () => import('@/view/chat/ChatInitialPage.vue'),
            },
            {
                path: 'chat/:chatroomId',
                name: 'ChatRoom',
                component: () => import('@/view/chat/ChatRoomPage.vue')
            }
        ]
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach(async (to, from, next) => {
    // const workspaceStore = useWorkspaceStore();
    const isAuthenticated = !!sessionStorage.getItem('userInfo');

    const proceed = () => {
        if (to.matched.some(record => record.meta.requiresAuth)) {
            if (!isAuthenticated) {
                const loginRoute = {
                    path: '/user/login',
                    query: {}
                };
                if (to.params.workspaceId) {
                    loginRoute.query.workspaceId = to.params.workspaceId;
                }
                next(loginRoute);
            } else {
                next();
            }
        } else {
            next();
        }
    };

    if (to.params.workspaceId) {
        try {
            const response = await axiosInstance.get(`/api/workspace/my/all`);
            if (response.data && response.data.result) proceed();
        } catch (error) {
            console.error('Failed to fetch workspace details', error);
            proceed();
        }
    } else {
        proceed();
    }
});

export default router;