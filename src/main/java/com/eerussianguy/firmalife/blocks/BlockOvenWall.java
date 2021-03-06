package com.eerussianguy.firmalife.blocks;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import static com.eerussianguy.firmalife.init.StatePropertiesFL.CURED;
import static net.dries007.tfc.Constants.RNG;
import static net.minecraft.block.BlockHorizontal.FACING;

public class BlockOvenWall extends Block implements IItemSize
{
    public static final AxisAlignedBB OVEN_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 9.0 / 16, 16.0D / 16, 16.0D / 16, 16.0D / 16);
    public static final AxisAlignedBB OVEN_WALL_EAST = new AxisAlignedBB(0.0D, 0.0D, 7.0D / 16, 16.0D / 16, 16.0D / 16, 0.0D);
    public static final AxisAlignedBB OVEN_WALL_NORTH = new AxisAlignedBB(7.0D / 16, 0.0D, 0.0D, 0.0D, 16.0D / 16, 16.0D / 16);
    public static final AxisAlignedBB OVEN_WALL_SOUTH = new AxisAlignedBB(9.0D / 16, 0.0D, 0.0D, 16.0D / 16, 16.0D / 16, 16.0D / 16);


    public BlockOvenWall()
    {
        super(Material.ROCK, MapColor.RED_STAINED_HARDENED_CLAY);
        setHardness(2.0f);
        setResistance(3.0f);
        setLightOpacity(0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(CURED, false).withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (facing.getAxis() == EnumFacing.Axis.Y)
        {
            facing = placer.getHorizontalFacing().getOpposite();
        }
        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta)).withProperty(CURED, meta > 3);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(CURED) ? 4 : 0);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return Size.NORMAL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return Weight.HEAVY;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, CURED);
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING))
        {
            case NORTH:
            default:
                return OVEN_WALL_NORTH;
            case SOUTH:
                return OVEN_WALL_SOUTH;
            case WEST:
                return OVEN_WALL_WEST;
            case EAST:
                return OVEN_WALL_EAST;
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        if (state.getValue(CURED))
        {
            drops.add(new ItemStack(Items.BRICK, 3 + RNG.nextInt(3)));
        }
        else
        {
            drops.add(new ItemStack(Items.CLAY_BALL, 3 + RNG.nextInt(3)));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}
