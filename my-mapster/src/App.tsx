import './App.css'
import {Route, Routes} from "react-router-dom";
import DefaultLayout from "./containers/default/DefaultLayout.tsx";
import CategoryListPage from "./category/list/CategoryListPage.tsx";
import CategoryCreatePage from "./category/create/CategoryCreatePage.tsx";
import CategoryUpdatePage from "./category/update/CategoryUpdatePage.tsx";

function App() {

    return (
        <>
            <Routes>
                <Route path={"/"} element={<DefaultLayout/>}>
                    <Route index element={<CategoryListPage/>}/>
                    <Route path={"category"}>
                        <Route path={"create"} element={<CategoryCreatePage/>}/>
                        <Route path={"update/:id"} element={<CategoryUpdatePage />} />
                    </Route>
                </Route>
            </Routes>
        </>
    )
}

export default App