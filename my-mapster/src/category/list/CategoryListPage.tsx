import { useState, useEffect } from "react";
import { Button, Modal } from "antd";
import { Link } from "react-router-dom";
import axios from "axios";

const CategoryListPage = () => {
    const [categories, setCategories] = useState<{ id: number, name: string }[]>([]);
    const [deleteModalVisible, setDeleteModalVisible] = useState(false);
    const [categoryIdToDelete, setCategoryIdToDelete] = useState<number | null>(null);

    useEffect(() => {
        axios.get("http://localhost:8088/api/categories")
            .then(response => {
                setCategories(response.data);
            })
            .catch(error => {
                console.error("Error fetching categories:", error);
            });
    }, []);

    const handleDeleteCategory = async (categoryId: number) => {
        try {
            await axios.delete(`http://localhost:8088/api/categories/${categoryId}`);
            // Після успішного видалення оновіть стан категорій
            axios.get("http://localhost:8088/api/categories")
                .then(response => {
                    setCategories(response.data);
                })
                .catch(error => {
                    console.error("Error fetching categories:", error);
                });
            setDeleteModalVisible(false);
        } catch (error) {
            console.error("Error deleting category:", error);
        }
    };

    return (
        <>
            <h1>Список категорій</h1>
            <Link to={"/category/create"}>
                <Button type="primary" size={"large"}>
                    Додати
                </Button>
            </Link>
            {categories.map(category => (
                <div key={category.id}>
                    <Link to={`/category/update/${category.id}`}>
                        <Button type="primary" size={"large"}>
                            {category.name} - Редагувати
                        </Button>
                    </Link>
                    <Button type="primary" size={"large"} onClick={() => {
                        setCategoryIdToDelete(category.id);
                        setDeleteModalVisible(true);
                    }}>
                        Видалити
                    </Button>
                </div>
            ))}
            <Modal
                title="Видалити категорію"
                visible={deleteModalVisible}
                onOk={() => handleDeleteCategory(categoryIdToDelete!)}
                onCancel={() => setDeleteModalVisible(false)}
            >
                <p>Ви впевнені, що хочете видалити цю категорію?</p>
            </Modal>
        </>
    );
};

export default CategoryListPage;
