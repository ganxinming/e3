package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Badribbit
 * @create 2019/6/18 10:04
 * @Define 查询所有ItemCat节点
 * @Tutorials
 * @Opinion
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getCatList(Long parentId) {
        TbItemCatExample example=new TbItemCatExample();
        Criteria criteria=example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list=itemCatMapper.selectByExample(example);
        List<EasyUITreeNode> listCat=new ArrayList<>();
        for (TbItemCat item : list){
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(item.getId());
            node.setText(item.getName());
            node.setState(item.getIsParent()?"closed":"open");
            listCat.add(node);
        }
        return listCat;
    }
}
